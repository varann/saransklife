package ru.saransklife.client.place;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

import ru.saransklife.client.Dao;
import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.PlaceCategoriesResponse;

/**
 * A simple {@link Fragment} subclass.
 */
@EActivity(R.layout.activity_categories)
public class PlaceCategoriesActivity extends FragmentActivity implements SwipeRefreshLayout.OnRefreshListener {

	@ViewById DrawerLayout drawerLayout;
	@ViewById SwipeRefreshLayout refresh;
	@ViewById Toolbar toolbar;
	@ViewById GridView grid;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;
	@Extra String title;

	private ru.saransklife.client.place.CategoryAdapter adapter;

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

		adapter = new ru.saransklife.client.place.CategoryAdapter(this, dao.getPlaceCategoryCursor());
		grid.setAdapter(adapter);

		refresh.setOnRefreshListener(this);
		refresh.setColorSchemeResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_1, R.color.refresh_color_2);

		refresh.setProgressViewOffset(false, 0,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
		refresh.setRefreshing(true);
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
		refresh.setRefreshing(false);
		adapter.swapCursor(dao.getPlaceCategoryCursor());
		adapter.notifyDataSetChanged();
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
