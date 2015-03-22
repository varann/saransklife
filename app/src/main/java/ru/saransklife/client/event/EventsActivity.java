package ru.saransklife.client.event;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;
import ru.saransklife.client.DataHelper;
import ru.saransklife.client.EventBus;
import ru.saransklife.client.Events;


@EActivity(R.layout.activity_events)
public class EventsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

	private static final int LOADER_ID = 0;

	@ViewById DrawerLayout drawerLayout;
	@ViewById Toolbar toolbar;
	@ViewById Spinner spinner;
	@ViewById SwipeRefreshLayout refresh;
	@ViewById RecyclerView recyclerView;

	@Extra String title;

	@Bean Dao dao;
	@Bean DataHelper dataHelper;
	@Bean EventBus eventBus;

	private EventsAdapter adapter;
	private DateType type = DateType.WEEK;
	private DateTypeAdapter dateTypeAdapter;

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

		dateTypeAdapter = new DateTypeAdapter();
		spinner.setAdapter(dateTypeAdapter);
		spinner.setSelection(3);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				type = (DateType) dateTypeAdapter.getItem(position);
				getLoaderManager().restartLoader(LOADER_ID, createForceBundle(false), new Callbacks());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new EventsAdapter(dao, type);
		recyclerView.setAdapter(adapter);

		refresh.setOnRefreshListener(this);
		refresh.setColorSchemeResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_1, R.color.refresh_color_2);
		refresh.setProgressViewOffset(false, 0,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

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
		setRefreshing(false);
	}

	public void onEvent(Events.EventsStartLoadingEvent event) {
		setRefreshing(true);
	}

	public void onEvent(Events.EventsLoadedEvent event) {
		getLoaderManager().restartLoader(LOADER_ID, createForceBundle(false), new Callbacks());
		setRefreshing(false);
	}

	public void onEvent(Events.EventsLoadErrorEvent event) {
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

	class Callbacks implements LoaderManager.LoaderCallbacks<Cursor> {

		@Override
		public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
			return new CursorLoader(getApplicationContext()) {
				@Override
				public Cursor loadInBackground() {
					return dataHelper.getEventCategoriesCursor(type.getType(), isForceBundle(args), getContext());
				}
			};
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			adapter.setDateType(type);
			adapter.swapCursor(cursor);
			adapter.notifyDataSetChanged();
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
		}
	}
}
