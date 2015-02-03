package ru.saransklife.client.place;

import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
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
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.PlaceEntitiesResponse;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;
import ru.saransklife.dao.PlaceCategory;

/**
 * A simple {@link Fragment} subclass.
 */
@EActivity(R.layout.activity_entities_by_category)
public class EntitiesByCategoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

	@ViewById Toolbar toolbar;
	@ViewById SwipeRefreshLayout refresh;
	@ViewById RecyclerView recyclerView;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;

	@Extra long category;

	private EntitiesAdapter adapter;


	@AfterViews
	void afterViews() {
		logExtra(new String[]{"category"}, Long.toString(category));

		PlaceCategory placeCategory = dao.getPlaceCategoryById(category);
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
		refresh.setRefreshing(true);
		loadEntities(placeCategory.getSlug());
	}

	@Background
	void loadEntities(String slug) {
		PlaceEntitiesResponse entities = apiClient.getPlaceEntities(slug);
		dao.setPlaceEntities(entities.getResponse().getEntities(), slug);
		updateEntities(slug);
	}

	@UiThread
	void updateEntities(String slug) {
		refresh.setRefreshing(false);
		adapter.swapCursor(dao.getPlaceEntitiesBySlugCursor(slug));
		adapter.notifyDataSetChanged();
	}

	@OptionsItem
	void homeSelected() {
		NavUtils.navigateUpFromSameTask(this);
	}

	@Override
	public void onRefresh() {
		refresh.setRefreshing(true);
		PlaceCategory placeCategory = dao.getPlaceCategoryById(category);
		loadEntities(placeCategory.getSlug());
	}
}
