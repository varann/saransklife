package ru.saransklife.client.reference;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import ru.saransklife.R;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;
import ru.saransklife.client.DataHelper;
import ru.saransklife.client.EventBus;
import ru.saransklife.client.Events;
import ru.saransklife.dao.Reference;
import ru.saransklife.dao.ReferenceCategory;

/**
 * Created by asavinova on 07/02/15.
 */
@EActivity(R.layout.activity_references)
public class ReferencesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

	private static final int LOADER_ID = 0;


	@ViewById Toolbar toolbar;
	@ViewById SwipeRefreshLayout refresh;
	@ViewById RecyclerView recyclerView;

	@Bean Dao dao;
	@Bean DataHelper dataHelper;
	@Bean EventBus eventBus;
	@Extra String slug;
	private ReferencesAdapter adapter;

	@AfterViews
	void afterViews() {
		logExtra(new String[]{"slug"}, slug);

		ReferenceCategory referenceCategory = dao.getReferenceCategoryBySlug(slug);

		toolbar.setTitle(referenceCategory.getName());
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new ReferencesAdapter(null);
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

	public void onEvent(Events.ReferencesStartLoadingEvent event) {
		setRefreshing(true);
	}

	public void onEvent(Events.ReferencesLoadedEvent event) {
		getLoaderManager().restartLoader(LOADER_ID, createForceBundle(false), new Callbacks());
		setRefreshing(false);
	}

	public void onEvent(Events.ReferencesLoadErrorEvent event) {
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

	class Callbacks implements LoaderManager.LoaderCallbacks<List<Reference>> {

		@Override
		public Loader<List<Reference>> onCreateLoader(int id, final Bundle args) {
			return new AsyncTaskLoader<List<Reference>>(getApplicationContext()) {
				@Override
				protected void onStartLoading() {
					super.onStartLoading();
					forceLoad();
				}

				@Override
				public List<Reference> loadInBackground() {
					List<Reference> references = dataHelper.getReferences(slug, isForceBundle(args), getContext());
					return references;
				}
			};
		}

		@Override
		public void onLoadFinished(Loader<List<Reference>> loader, List<Reference> data) {
			adapter.updateReferences(data);
			adapter.notifyDataSetChanged();
		}

		@Override
		public void onLoaderReset(Loader<List<Reference>> loader) {
		}
	}
}
