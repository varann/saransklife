package ru.saransklife.place;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import ru.saransklife.EventBus;
import ru.saransklife.R;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_container)
public class PlaceFragment extends Fragment {

	@Bean EventBus eventBus;

	private CategoriesFragment categoriesFragment = new CategoriesFragment_();
	private EntitiesByCategoryFragment entitiesFragment = new EntitiesByCategoryFragment_();
	private EntityFragment entityFragment = new EntityFragment_();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			getChildFragmentManager().beginTransaction()
					.add(R.id.container, categoriesFragment)
					.commit();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		eventBus.register(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		eventBus.unregister(this);
	}

	public void onEvent(OpenPlaceEntitiesEvent event) {
		entitiesFragment.setCategoryId(event.getId());
		showFragment(entitiesFragment);
	}

	public void onEvent(OpenPlaceEntityEvent event) {
		entityFragment.setId(event.getId());
		showFragment(entityFragment);
	}

	void showFragment(Fragment fragment) {
		getChildFragmentManager().beginTransaction()
				.addToBackStack(null)
				.replace(R.id.container, fragment)
				.commit();
	}

}
