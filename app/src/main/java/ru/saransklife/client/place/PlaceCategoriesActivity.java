package ru.saransklife.client.place;


import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
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

import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.PlaceCategoriesResponse;
import ru.saransklife.api.model.PlaceEntitiesResponse;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;

/**
 * A simple {@link Fragment} subclass.
 */
@EActivity(R.layout.activity_categories)
public class PlaceCategoriesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

	@ViewById DrawerLayout drawerLayout;
	@ViewById Toolbar toolbar;
	@ViewById InterestingViewPager viewpager;
	@ViewById SwipeRefreshLayout refresh;
	@ViewById GridView grid;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;
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

		categoryAdapter = new CategoryAdapter(this, dao.getPlaceCategoryCursor());
		grid.setAdapter(categoryAdapter);

		//TODO Временно заблокировано
		refresh.setEnabled(false);
		refresh.setOnRefreshListener(this);
		refresh.setColorSchemeResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_1, R.color.refresh_color_2);

		refresh.setProgressViewOffset(false, 0,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
		refresh.setRefreshing(true);
		loadCategories();
	}

	@Background
	void loadCategories() {
		PlaceEntitiesResponse places = apiClient.getInterestingPlaces();
		dao.setPlaceEntities(places.getResponse().getEntities(), Dao.INTERESTING_PLACES_SLUG);
		updateInterestingPlaces();

		PlaceCategoriesResponse categories = apiClient.getPlaceCategories();
		dao.setPlaceCategories(categories.getResponse());
		updateCategories();

		setRefreshing(false);
	}

	@UiThread
	void updateInterestingPlaces() {
		viewpager.update();
	}

	@UiThread
	void updateCategories() {
		categoryAdapter.swapCursor(dao.getPlaceCategoryCursor());
		categoryAdapter.notifyDataSetChanged();
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
		refresh.setRefreshing(true);
		loadCategories();
	}
}
