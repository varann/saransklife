package ru.saransklife.event;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import ru.saransklife.Dao;
import ru.saransklife.EventBus;
import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.EventCategoriesResponse;
import ru.saransklife.api.model.EventsResponse;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
@EFragment(R.layout.fragment_events)
public class EventsFragment extends Fragment {

	@ViewById RecyclerView recyclerView;

	@Bean Dao dao;
	@Bean EventBus eventBus;
	@RestService RestApiClient apiClient;
	private EventsAdapter adapter;

	@AfterViews
	void afterViews() {
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		//TODO Если передавать getChildFragmentManager(), то возникают проблемы при нажатии на Back
		adapter = new EventsAdapter(dao.getEventCategories(), getFragmentManager(), dao);
		recyclerView.setAdapter(adapter);

		loadEvents();
	}

	@Background
	void loadEvents() {
		EventCategoriesResponse categories = apiClient.getEventCategories();
		dao.setEventCategories(categories.getResponse());

		EventsResponse events = apiClient.getEvents();
		dao.setEvents(events.getResponse());

		updateUi();
	}

	@UiThread
	void updateUi() {
		adapter.swapCursor(dao.getEventCategories());
		adapter.notifyDataSetChanged();
	}
}
