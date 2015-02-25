package ru.saransklife.client;

import android.app.IntentService;
import android.content.Intent;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.MenuResponse;
import ru.saransklife.api.model.PageResponse;
import ru.saransklife.api.model.PlaceCategoriesResponse;
import ru.saransklife.api.model.PlaceEntitiesResponse;
import ru.saransklife.dao.Page;

/**
 * Created by asavinova on 12/02/15.
 */
@EIntentService
public class DataService extends IntentService {

	@RestService RestApiClient apiClient;
	@Bean Dao dao;
	@Bean EventBus eventBus;


	public DataService() {
		super("DataService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
	}

	@ServiceAction
	void menuAction() {
		try {
			MenuResponse response = apiClient.getMenu();
			dao.setMenuItems(response.getResponse());
			eventBus.post(new Events.MenuLoadedEvent());
		} catch (RestClientException e) {
			if (dao.getRootMenuItems().size() == 0) {
				eventBus.post(new Events.MenuLoadErrorEvent());
			} else {
				eventBus.post(new Events.MenuLoadedEvent());
			}
		}
	}

	@ServiceAction
	void pageAction(String slug) {
		Page page = dao.getPage(slug);
		if (page == null) {
			try {
				PageResponse response = apiClient.getPage(slug);
				page = response.getResponse();
				dao.setPage(page);
			} catch (RestClientException e) {
				eventBus.post(new Events.PageLoadErrorEvent(slug));
				return;
			}
		}

		eventBus.post(new Events.PageLoadedEvent(page));
	}

	@ServiceAction
	void interestingPlacesAction() {
		try {
			PlaceEntitiesResponse places = apiClient.getInterestingPlaces();
			dao.setPlaceEntities(places.getResponse().getEntities(), Dao.INTERESTING_PLACES_SLUG);
		} catch (RestClientException e) {
			eventBus.post(new Events.InterestingPlacesLoadErrorEvent());
		}

		eventBus.post(new Events.InterestingPlacesLoadedEvent());
	}

	@ServiceAction
	void placeCategoriesAction() {
		try {
			PlaceCategoriesResponse categories = apiClient.getPlaceCategories();
			dao.setPlaceCategories(categories.getResponse());
		} catch (RestClientException e) {
			eventBus.post(new Events.PlaceCategoriesLoadErrorEvent());
		}

		eventBus.post(new Events.PlaceCategoriesLoadedEvent());
	}
}
