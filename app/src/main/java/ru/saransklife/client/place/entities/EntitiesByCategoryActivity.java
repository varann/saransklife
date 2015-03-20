package ru.saransklife.client.place.entities;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import ru.saransklife.R;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;
import ru.saransklife.client.DataHelper;
import ru.saransklife.client.EventBus;
import ru.saransklife.client.Events;
import ru.saransklife.dao.PlaceCategory;

/**
 * A simple {@link Fragment} subclass.
 */
@EActivity(R.layout.activity_entities_by_category)
public class EntitiesByCategoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

	private static final int ENTITIES_LOADER_ID = 0;
	private static final int SUBCATEGORIES_LOADER_ID = 1;

	@ViewById Toolbar toolbar;
	@ViewById Spinner spinner;
	@ViewById SwipeRefreshLayout refresh;
	@ViewById RecyclerView recyclerView;

	@Bean Dao dao;
	@Bean DataHelper dataHelper;
	@Bean EventBus eventBus;

	@Extra long category;

	private EntitiesAdapter adapter;
	private PlaceCategory placeCategory;
	private SubCategoryAdapter categoryAdapter;
	private String slug;

	@AfterViews
	void afterViews() {
		logExtra(new String[]{"category"}, Long.toString(category));

		placeCategory = dao.getPlaceCategoryById(category);
		slug = placeCategory.getSlug();
		toolbar.setTitle(placeCategory.getName());

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		trySetSpinner();

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new EntitiesAdapter(null);
		recyclerView.setAdapter(adapter);

		refresh.setOnRefreshListener(this);
		refresh.setColorSchemeResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_1, R.color.refresh_color_2);

		refresh.setProgressViewOffset(false, 0,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

	}

	private void trySetSpinner() {
		categoryAdapter = new SubCategoryAdapter();
		spinner.setAdapter(categoryAdapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				PlaceCategory category = (PlaceCategory) categoryAdapter.getItem(position);
				slug = category.getSlug();
				getLoaderManager().restartLoader(ENTITIES_LOADER_ID, createForceBundle(false), new EntitiesCallbacks());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		getLoaderManager().initLoader(SUBCATEGORIES_LOADER_ID, null, new SubCategoriesCallbacks());
	}

	@Override
	public void onResume() {
		super.onResume();
		eventBus.register(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		eventBus.unregister(this);
	}

	public void onEvent(Events.SubPlaceCategoriesStartLoadingEvent event) {
		setRefreshing(true);
	}

	public void onEvent(Events.SubPlaceCategoriesLoadedEvent event) {
		getLoaderManager().restartLoader(SUBCATEGORIES_LOADER_ID, null, new SubCategoriesCallbacks());
		setRefreshing(false);
	}

	public void onEvent(Events.SubPlaceCategoriesLoadErrorEvent event) {
		setRefreshing(false);
		showErrorDialog(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getLoaderManager().restartLoader(SUBCATEGORIES_LOADER_ID, null, new SubCategoriesCallbacks());
			}
		});
	}

	public void onEvent(Events.PlaceEntitiesStartLoadingEvent event) {
		setRefreshing(true);
	}

	public void onEvent(Events.PlaceEntitiesLoadedEvent event) {
		getLoaderManager().restartLoader(ENTITIES_LOADER_ID, createForceBundle(false), new EntitiesCallbacks());
		setRefreshing(false);
	}

	public void onEvent(Events.PlaceEntitiesLoadErrorEvent event) {
		setRefreshing(false);
		showErrorDialog(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getLoaderManager().restartLoader(ENTITIES_LOADER_ID, createForceBundle(true), new EntitiesCallbacks());
			}
		});
	}

	@UiThread
	void setRefreshing(boolean refreshing) {
		refresh.setRefreshing(refreshing);
	}

	@Override
	public void onRefresh() {
		getLoaderManager().restartLoader(ENTITIES_LOADER_ID, createForceBundle(true), new EntitiesCallbacks());
	}


	class EntitiesCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

		@Override
		public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
			return new CursorLoader(getApplicationContext()) {
				@Override
				public Cursor loadInBackground() {
					return dataHelper.getPlaceEntitiesCursor(slug, isForceBundle(args), getContext());
				}
			};
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			adapter.swapCursor(cursor);
			adapter.notifyDataSetChanged();
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
		}
	}

	class SubCategoriesCallbacks implements LoaderManager.LoaderCallbacks<List<PlaceCategory>> {

		@Override
		public Loader<List<PlaceCategory>> onCreateLoader(int id, Bundle args) {
			return new AsyncTaskLoader<List<PlaceCategory>>(getApplicationContext()) {
				@Override
				protected void onStartLoading() {
					super.onStartLoading();
					forceLoad();
				}

				@Override
				public List<PlaceCategory> loadInBackground() {
					return dataHelper.getSubPlaceCategories(placeCategory.getSlug(), getContext());
				}
			};
		}

		@Override
		public void onLoadFinished(Loader<List<PlaceCategory>> loader, List<PlaceCategory> data) {
			if (data != null && !data.isEmpty()) {
				spinner.setVisibility(View.VISIBLE);
				getSupportActionBar().setDisplayShowTitleEnabled(false);

				List<PlaceCategory> categories = new ArrayList<>();
				categories.add(placeCategory);
				categories.addAll(data);
				categoryAdapter.setCategories(categories);
				categoryAdapter.notifyDataSetChanged();
			} else {
				spinner.setVisibility(View.GONE);
			}

			getLoaderManager().restartLoader(ENTITIES_LOADER_ID, createForceBundle(false), new EntitiesCallbacks());
		}

		@Override
		public void onLoaderReset(Loader<List<PlaceCategory>> loader) {
		}
	}
}
