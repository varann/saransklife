package ru.saransklife.place;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import ru.saransklife.Dao;
import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.PlaceEntitiesResponse;
import ru.saransklife.dao.PlaceCategory;

/**
 * A simple {@link Fragment} subclass.
 */
@EActivity(R.layout.activity_entities_by_category)
public class EntitiesByCategoryActivity extends ActionBarActivity {

	@ViewById Toolbar toolbar;
	@ViewById RecyclerView recyclerView;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;

	@Extra long category;

	private EntitiesAdapter adapter;


	@AfterViews
	void afterViews() {
		PlaceCategory placeCategory = dao.getPlaceCategoryById(category);
		toolbar.setTitle(placeCategory.getName());

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new EntitiesAdapter(dao.getPlaceEntitiesCursor());
		recyclerView.setAdapter(adapter);

		loadEntities(placeCategory.getSlug());
	}

	@Background
	void loadEntities(String slug) {
		PlaceEntitiesResponse entities = apiClient.getPlaceEntities(slug);
		dao.setPlaceEntities(entities.getResponse().getEntities());
		updateEntities();
	}

	@UiThread
	void updateEntities() {
		adapter.swapCursor(dao.getPlaceEntitiesCursor());
		adapter.notifyDataSetChanged();
	}

	@OptionsItem
	void homeSelected() {
		NavUtils.navigateUpFromSameTask(this);
	}
}
