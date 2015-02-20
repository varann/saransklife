package ru.saransklife.client.place;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.DataHelper;
import ru.saransklife.client.EventBus;
import ru.saransklife.client.Events;

/**
 * A simple {@link Fragment} subclass.
 */
@EActivity(R.layout.activity_categories)
public class PlaceCategoriesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {

	private static final int LOADER_ID = 0;

	@ViewById DrawerLayout drawerLayout;
	@ViewById Toolbar toolbar;
	@ViewById SwipeRefreshLayout refresh;
	@ViewById GridView grid;
	@FragmentById InterestingViewPagerFragment interestingViewPager;

	@Bean DataHelper dataHelper;
	@Bean EventBus eventBus;
	@Extra String title;

	private CategoryAdapter categoryAdapter;

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

		categoryAdapter = new CategoryAdapter(this, null);
		grid.setAdapter(categoryAdapter);

		refresh.setOnRefreshListener(this);
		refresh.setColorSchemeResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_1, R.color.refresh_color_2);

		refresh.setProgressViewOffset(false, 0,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

		getLoaderManager().initLoader(LOADER_ID, createForceBundle(false), this);
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

	public void onEvent(Events.PlaceCategoriesLoadedEvent event) {
		getLoaderManager().restartLoader(LOADER_ID, createForceBundle(false), this);
	}

	public void onEvent(Events.PlaceCategoriesLoadErrorEvent event) {
		setRefreshing(false);
		showErrorDialog(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getLoaderManager().restartLoader(LOADER_ID, createForceBundle(true), PlaceCategoriesActivity.this);
			}
		});
	}

	@UiThread
	void setRefreshing(boolean refreshing) {
		refresh.setRefreshing(refreshing);
	}

	@ItemClick
	void gridItemClicked(int position) {
		long id = grid.getAdapter().getItemId(position);
		EntitiesByCategoryActivity_.intent(this).category(id).start();
	}

	@Override
	public void onRefresh() {
		getLoaderManager().restartLoader(LOADER_ID, createForceBundle(true), this);
		interestingViewPager.forceRefresh();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
		return new CursorLoader(this) {
			@Override
			protected void onStartLoading() {
				super.onStartLoading();
				setRefreshing(true);
			}

			@Override
			public Cursor loadInBackground() {
				return dataHelper.getPlaceCategoriesCursor(isForceBundle(args), getContext());
			}
		};
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		setRefreshing(false);
		categoryAdapter.swapCursor(cursor);
		categoryAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

}
