package ru.saransklife.place;


import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import ru.saransklife.EventBus;
import ru.saransklife.R;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_place)
public class PlaceFragment extends Fragment {

	@Bean EventBus eventBus;

	@AfterViews
	void afterViews(){
		showFragment(new CategoriesFragment_());
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
		EntitiesByCategoryFragment entitiesFragment =
				EntitiesByCategoryFragment_.builder().
						categoryId(event.getId()).
						build();

		showFragment(entitiesFragment);
	}

	void showFragment(Fragment fragment) {
		getChildFragmentManager().beginTransaction()
				.addToBackStack(null)
				.replace(R.id.container, fragment)
				.commit();
	}

}
