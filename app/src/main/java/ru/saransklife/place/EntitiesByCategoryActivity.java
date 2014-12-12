package ru.saransklife.place;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentArg;
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
public class EntitiesByCategoryActivity extends Activity {

	@ViewById
	RecyclerView recyclerView;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;

	@Extra long category;

	private EntitiesAdapter adapter;


	@AfterViews
	void afterViews() {
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new EntitiesAdapter(dao.getPlaceEntitiesCursor());
		recyclerView.setAdapter(adapter);

		loadEntities();
	}

	@Background
	void loadEntities() {
		PlaceCategory placeCategory = dao.getPlaceCategoryById(category);
		PlaceEntitiesResponse entities = apiClient.getPlaceEntities(placeCategory.getSlug());
		dao.setPlaceEntities(entities.getResponse().getEntities());
		updateEntities();
	}

	@UiThread
	void updateEntities() {
		adapter.swapCursor(dao.getPlaceEntitiesCursor());
		adapter.notifyDataSetChanged();
	}

}
