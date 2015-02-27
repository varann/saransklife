package ru.saransklife.client.reference;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import ru.saransklife.R;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.DataHelper;
import ru.saransklife.client.EventBus;
import ru.saransklife.client.Events;
import ru.saransklife.dao.ReferenceCategory;

/**
 * Created by asavinova on 06/02/15.
 */
@EActivity(R.layout.activity_reference_categories)
public class ReferenceCategoriesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

	private static final int LOADER_ID = 0;

	@ViewById DrawerLayout drawerLayout;
	@ViewById Toolbar toolbar;
	@ViewById SwipeRefreshLayout refresh;
	@ViewById RecyclerView recyclerView;

	@Bean DataHelper dataHelper;
	@Bean EventBus eventBus;

	@Extra String title;
	private ReferenceCategoryAdapter adapter;

	@AfterViews
	void afterViews() {
		logExtra(new String[]{"title"}, title);

		toolbar.setTitle(title);
		toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawerLayout.openDrawer(Gravity.START);
			}
		});

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new ReferenceCategoryAdapter(null);
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

	public void onEvent(Events.ReferenceCategoriesStartLoadingEvent event) {
		setRefreshing(true);
	}

	public void onEvent(Events.ReferenceCategoriesLoadedEvent event) {
		getLoaderManager().restartLoader(LOADER_ID, createForceBundle(false), new Callbacks());
		setRefreshing(false);
	}

	public void onEvent(Events.ReferenceCategoriesLoadErrorEvent event) {
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

	@Override
	public void onRefresh() {
		getLoaderManager().restartLoader(LOADER_ID, createForceBundle(true), new Callbacks());
	}

	class Callbacks implements LoaderManager.LoaderCallbacks<List<ReferenceCategory>> {

		@Override
		public Loader<List<ReferenceCategory>> onCreateLoader(int id, final Bundle args) {
			return new AsyncTaskLoader<List<ReferenceCategory>>(getApplicationContext()) {
				@Override
				protected void onStartLoading() {
					super.onStartLoading();
					forceLoad();
				}

				@Override
				public List<ReferenceCategory> loadInBackground() {
					List<ReferenceCategory> referenceCategories = dataHelper.getReferenceCategories(isForceBundle(args), getContext());
					return referenceCategories;
				}
			};
		}

		@Override
		public void onLoadFinished(Loader<List<ReferenceCategory>> loader, List<ReferenceCategory> data) {
			adapter.updateCategories(data);
			adapter.notifyDataSetChanged();
		}

		@Override
		public void onLoaderReset(Loader<List<ReferenceCategory>> loader) {
		}
	}

}