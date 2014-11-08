package ru.saransklife.event;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import ru.saransklife.EventBus;
import ru.saransklife.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
@EFragment(R.layout.fragment_container)
public class EventMainFragment extends Fragment {

	@Bean EventBus eventBus;
	private EventsFragment eventsFragment = new EventsFragment_();
	private EventInfoFragment eventInfoFragment = new EventInfoFragment_();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			getChildFragmentManager().beginTransaction()
					.add(R.id.container, eventsFragment)
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

	public void onEvent(OpenEventEvent event) {
		eventInfoFragment.setId(event.getId());
		showFragment(eventInfoFragment);
	}

	void showFragment(Fragment fragment) {
		getChildFragmentManager().beginTransaction()
				.addToBackStack(null)
				.replace(R.id.container, fragment)
				.commit();
	}

}
