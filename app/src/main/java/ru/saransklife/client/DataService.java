package ru.saransklife.client;

import android.app.IntentService;
import android.content.Intent;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.EventCategoriesResponse;
import ru.saransklife.api.model.EventsResponse;
import ru.saransklife.api.model.MenuResponse;
import ru.saransklife.api.model.PageResponse;
import ru.saransklife.api.model.PlaceCategoriesResponse;
import ru.saransklife.api.model.PlaceEntitiesResponse;
import ru.saransklife.api.model.ReferenceCategoriesResponse;
import ru.saransklife.api.model.ReferencesResponse;
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
			dao.setPlaceEntities(places.getResponse().getEntities(), Dao.Request.INTERESTING_PLACES, Dao.INTERESTING_PLACES_SLUG);
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

	@ServiceAction
	void placeEntitiesAction(String slug) {
		try {
			PlaceEntitiesResponse entities = apiClient.getPlaceEntities(slug);
			dao.setPlaceEntities(entities.getResponse().getEntities(), Dao.Request.PLACE_ENTITIES, slug);
		} catch (RestClientException e) {
			eventBus.post(new Events.PlaceEntitiesLoadErrorEvent());
		}

		eventBus.post(new Events.PlaceEntitiesLoadedEvent());
	}

	@ServiceAction
	void eventsAndCategoriesAction() {
		try {
			EventCategoriesResponse categories = apiClient.getEventCategories();
			dao.setEventCategories(categories.getResponse());

			EventsResponse events = apiClient.getEvents();
			dao.setEvents(events.getResponse());

			dao.setLastUpdated(Dao.Request.EVENTS_AND_CATEGORIES, null);
		} catch (RestClientException e) {
			eventBus.post(new Events.EventsAndCategoriesLoadErrorEvent());
		}

		eventBus.post(new Events.EventsAndCategoriesLoadedEvent());
	}

	@ServiceAction
	void referenceCategoriesAction() {
		try {
			ReferenceCategoriesResponse categories = apiClient.getReferenceCategories();
			dao.setReferenceCategories(categories.getResponse());
		} catch (RestClientException e) {
			eventBus.post(new Events.ReferenceCategoriesLoadErrorEvent());
		}

		eventBus.post(new Events.ReferenceCategoriesLoadedEvent());
	}

	@ServiceAction
	void referencesAction(String slug) {
		try {
			ReferencesResponse data = apiClient.getReferences(slug);
			dao.setReferences(data.getResponse().getEntities(), slug);
		} catch (RestClientException e) {
			eventBus.post(new Events.ReferencesLoadErrorEvent());
		}

		eventBus.post(new Events.ReferencesLoadedEvent());
	}
}
