package ru.saransklife.place;


import android.support.v4.app.Fragment;
import android.widget.GridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.api.BackgroundExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.saransklife.Dao;
import ru.saransklife.EventBus;
import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.PlaceCategoriesResponse;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_categories)
public class CategoriesFragment extends Fragment {

	@ViewById GridView grid;

	@Bean Dao dao;
	@Bean EventBus eventBus;
	@RestService RestApiClient apiClient;
	private CategoryAdapter adapter;

	@AfterViews
	void afterViews() {
		adapter = new CategoryAdapter(getActivity(), dao.getPlaceCategoryCursor());
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
		eventBus.post(new OpenPlaceEntitiesEvent(id));
	}
}
