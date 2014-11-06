package ru.saransklife.place;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
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
@EFragment(R.layout.fragment_entities_by_category)
public class EntitiesByCategoryFragment extends Fragment {

	@FragmentArg
	long categoryId;

	@ViewById
	RecyclerView recyclerView;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;
	private EntitiesAdapter adapter;

	@AfterViews
	void afterViews() {
		getEntities();

		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		adapter = new EntitiesAdapter(dao.getPlaceEntitiesCursor());
		recyclerView.setAdapter(adapter);
	}

	@Background
	void getEntities() {
		PlaceCategory category = dao.getPlaceCategoryById(categoryId);
		PlaceEntitiesResponse entities = apiClient.getPlaceEntities(category.getSlug());
		dao.setPlaceEntities(entities.getResponse().getEntities());
		updateEntities();
	}

	@UiThread
	void updateEntities() {
		adapter.notifyDataSetChanged();
	}
}
