package ru.saransklife.event;


import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import ru.saransklife.EventBus;
import ru.saransklife.R;
import ru.saransklife.place.CategoriesFragment_;
import ru.saransklife.place.EntitiesByCategoryFragment;
import ru.saransklife.place.EntitiesByCategoryFragment_;
import ru.saransklife.place.EntityFragment;
import ru.saransklife.place.EntityFragment_;
import ru.saransklife.place.OpenPlaceEntitiesEvent;
import ru.saransklife.place.OpenPlaceEntityEvent;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
@EFragment(R.layout.fragment_container)
public class EventMainFragment extends Fragment {

	@Bean EventBus eventBus;

	@Override
	public void onResume() {
		super.onResume();
		eventBus.register(this);
		showFragment(new EventsFragment_());
	}

	@Override
	public void onPause() {
		super.onPause();
		eventBus.unregister(this);
	}

	public void onEvent(OpenEventEvent event) {
		EventInfoFragment eventFragment =
				EventInfoFragment_.builder().
						id(event.getId()).
						build();

		showFragment(eventFragment);
	}

	void showFragment(Fragment fragment) {
		getChildFragmentManager().beginTransaction()
				.addToBackStack(null)
				.replace(R.id.container, fragment)
				.commit();
	}

}
