package ru.saransklife.place;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.GridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import ru.saransklife.Dao;
import ru.saransklife.EventBus;
import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.PlaceCategoriesResponse;

/**
 * A simple {@link Fragment} subclass.
 */
@EActivity(R.layout.activity_categories)
public class PlaceCategoriesActivity extends FragmentActivity {

	@ViewById GridView grid;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;
	private CategoryAdapter adapter;

	@AfterViews
	void afterViews() {
		adapter = new CategoryAdapter(this, dao.getPlaceCategoryCursor());
		grid.setAdapter(adapter);

		loadCategories();
	}

	@Background
	void loadCategories() {
		PlaceCategoriesResponse categories = apiClient.getPlaceCategories();
		dao.setPlaceCategories(categories.getResponse());
		updateCategories();
	}

	@UiThread
	void updateCategories() {
		adapter.swapCursor(dao.getPlaceCategoryCursor());
		adapter.notifyDataSetChanged();
	}

	@ItemClick
	void gridItemClicked(int position) {
		long id = grid.getAdapter().getItemId(position);
		EntitiesByCategoryActivity_.intent(this).category(id).start();
	}
}
