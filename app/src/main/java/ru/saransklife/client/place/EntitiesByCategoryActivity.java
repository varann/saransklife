package ru.saransklife.client.place;

import android.app.LoaderManager;
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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

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

	private static final int LOADER_ID = 0;

	@ViewById Toolbar toolbar;
	@ViewById SwipeRefreshLayout refresh;
	@ViewById RecyclerView recyclerView;

	@Bean Dao dao;
	@Bean DataHelper dataHelper;
	@Bean EventBus eventBus;

	@Extra long category;

	private EntitiesAdapter adapter;
	private PlaceCategory placeCategory;


	@AfterViews
	void afterViews() {
		logExtra(new String[]{"category"}, Long.toString(category));

		placeCategory = dao.getPlaceCategoryById(category);
		toolbar.setTitle(placeCategory.getName());

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new ru.saransklife.client.place.EntitiesAdapter(dao.getPlaceEntitiesBySlugCursor(placeCategory.getSlug()));
		recyclerView.setAdapter(adapter);

		refresh.setOnRefreshListener(this);
		refresh.setColorSchemeResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_1, R.color.refresh_color_2);

		refresh.setProgressViewOffset(false, 0,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

		getLoaderManager().initLoader(LOADER_ID, createForceBundle(false), new Callbacks());
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

	public void onEvent(Events.PlaceEntitiesStartLoadingEvent event) {
		setRefreshing(true);
	}

	public void onEvent(Events.PlaceEntitiesLoadedEvent event) {
		getLoaderManager().restartLoader(LOADER_ID, createForceBundle(false), new Callbacks());
		setRefreshing(false);
	}

	public void onEvent(Events.PlaceEntitiesLoadErrorEvent event) {
		setRefreshing(false);
		showErrorDialog(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getLoaderManager().restartLoader(LOADER_ID, createForceBundle(true), new Callbacks());
			}
		});
	}

	@UiThread
	void setRefreshing(boolean refreshing) {
		refresh.setRefreshing(refreshing);
	}

	@OptionsItem
	void homeSelected() {
		NavUtils.navigateUpFromSameTask(this);
	}

	@Override
	public void onRefresh() {
		getLoaderManager().restartLoader(LOADER_ID, createForceBundle(true), new Callbacks());
	}


	class Callbacks implements LoaderManager.LoaderCallbacks<Cursor> {

		@Override
		public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
			return new CursorLoader(getApplicationContext()) {
				@Override
				public Cursor loadInBackground() {
					return dataHelper.getPlaceEntitiesCursor(placeCategory.getSlug(), isForceBundle(args), getContext());
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
}
