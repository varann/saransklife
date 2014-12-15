package ru.saransklife.place;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import ru.saransklife.Dao;
import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.PlaceCategoriesResponse;

/**
 * A simple {@link Fragment} subclass.
 */
@EActivity(R.layout.activity_categories)
public class PlaceCategoriesActivity extends FragmentActivity {

	@ViewById DrawerLayout drawerLayout;
	@ViewById Toolbar toolbar;
	@ViewById GridView grid;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;
	@Extra String title;

	private CategoryAdapter adapter;

	@AfterViews
	void afterViews() {
		toolbar.setTitle(title);
		toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawerLayout.openDrawer(Gravity.START);
			}
		});

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
