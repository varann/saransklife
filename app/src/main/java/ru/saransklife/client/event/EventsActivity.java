package ru.saransklife.client.event;


import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import ru.saransklife.client.Dao;
import ru.saransklife.client.EventBus;
import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.EventCategoriesResponse;
import ru.saransklife.api.model.EventsResponse;


@EActivity(R.layout.activity_events)
public class EventsActivity extends FragmentActivity {

	@ViewById DrawerLayout drawerLayout;
	@ViewById Toolbar toolbar;
	@ViewById RecyclerView recyclerView;

	@Extra String title;

	@Bean Dao dao;
	@Bean EventBus eventBus;
	@RestService RestApiClient apiClient;

	private EventsAdapter adapter;

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

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new EventsAdapter(dao.getEventCategories(), getSupportFragmentManager(), dao);
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
