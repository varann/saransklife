package ru.saransklife.client.reference;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.ReferencesResponse;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;
import ru.saransklife.dao.ReferenceCategory;

/**
 * Created by asavinova on 07/02/15.
 */
@EActivity(R.layout.activity_references)
public class ReferencesActivity extends BaseActivity {

	@ViewById Toolbar toolbar;
	@ViewById SwipeRefreshLayout refresh;
	@ViewById RecyclerView recyclerView;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;
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
		adapter = new ReferencesAdapter(dao.getReferences(slug));
		recyclerView.setAdapter(adapter);

		refresh.setColorSchemeResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_1, R.color.refresh_color_2);
		refresh.setProgressViewOffset(false, 0,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
		refresh.setEnabled(false);
		refresh.setRefreshing(true);
		loadData();
	}

	@Background
	void loadData() {
		ReferencesResponse data = apiClient.getReferences(slug);
		dao.setReferences(data.getResponse().getEntities(), slug);

		updateUi();
	}

	@UiThread
	void updateUi() {
		refresh.setRefreshing(false);
		adapter.updateReferences(dao.getReferences(slug));
		adapter.notifyDataSetChanged();
	}
}
