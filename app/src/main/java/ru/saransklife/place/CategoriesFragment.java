package ru.saransklife.place;


import android.support.v4.app.Fragment;
import android.widget.GridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
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
@EFragment(R.layout.fragment_categories)
public class CategoriesFragment extends Fragment {

	@ViewById GridView grid;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;

	@AfterViews
	void afterViews() {
		getCategories();
	}

	@Background
	void getCategories() {
		PlaceCategoriesResponse categories = apiClient.getPlaceCategories();
		dao.setPlaceCategories(categories.getResponse());
		updateCategories();
	}

	@UiThread
	void updateCategories() {
		grid.setAdapter(new CategoryAdapter(getActivity(), dao.getPlaceCategoryCursor()));
	}

}
