package ru.saransklife.client;

import android.app.IntentService;
import android.content.Intent;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;

import retrofit.RetrofitError;
import ru.saransklife.api.ApiCLient;
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

	@Bean Dao dao;
	@Bean EventBus eventBus;
	@Bean ApiCLient api;


	public DataService() {
		super("DataService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
	}

	@ServiceAction
	void menuAction() {
		try {
			MenuResponse response = api.getMenu();
			dao.setMenuItems(response.getResponse());
			eventBus.post(new Events.MenuLoadedEvent());
		} catch (RetrofitError e) {
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
				PageResponse response = api.getPage(slug);
				page = response.getResponse();
				dao.setPage(page);
			} catch (RetrofitError e) {
				eventBus.post(new Events.PageLoadErrorEvent(slug));
				return;
			}
		}

		eventBus.post(new Events.PageLoadedEvent(page));
	}

	@ServiceAction
	void interestingPlacesAction() {
		try {
			PlaceEntitiesResponse places = api.getInterestingPlaces();
			dao.setPlaceEntities(places.getResponse().getEntities(), Dao.Request.INTERESTING_PLACES, Dao.INTERESTING_PLACES_SLUG);
			eventBus.post(new Events.InterestingPlacesLoadedEvent());
		} catch (RetrofitError e) {
			eventBus.post(new Events.InterestingPlacesLoadErrorEvent());
		}
	}

	@ServiceAction
	void placeCategoriesAction() {
		try {
			PlaceCategoriesResponse categories = api.getPlaceCategories();
			dao.setPlaceCategories(categories.getResponse(), null);
			eventBus.post(new Events.PlaceCategoriesLoadedEvent());
		} catch (RetrofitError e) {
			eventBus.post(new Events.PlaceCategoriesLoadErrorEvent());
		}
	}

	@ServiceAction
	void subPlaceCategoriesAction(String slug) {
		try {
			PlaceCategoriesResponse categories = api.getSubPlaceCategories(slug);
			dao.setPlaceCategories(categories.getResponse(), slug);
			eventBus.post(new Events.SubPlaceCategoriesLoadedEvent());
		} catch (RetrofitError e) {
			eventBus.post(new Events.SubPlaceCategoriesLoadErrorEvent());
		}
	}

	@ServiceAction
	void placeEntitiesAction(String slug) {
		try {
			PlaceEntitiesResponse entities = api.getPlaceEntities(slug);
			dao.setPlaceEntities(entities.getResponse().getEntities(), Dao.Request.PLACE_ENTITIES, slug);
			eventBus.post(new Events.PlaceEntitiesLoadedEvent());
		} catch (RetrofitError e) {
			eventBus.post(new Events.PlaceEntitiesLoadErrorEvent());
		}
	}

	@ServiceAction
	void eventsAction(String type) {
		try {
			EventsResponse events = api.getEvents(type);
			dao.setEvents(events.getResponse(), type);
			eventBus.post(new Events.EventsLoadedEvent());
		} catch (RetrofitError e) {
			eventBus.post(new Events.EventsLoadErrorEvent());
		}
	}

	@ServiceAction
	void referenceCategoriesAction() {
		try {
			ReferenceCategoriesResponse categories = api.getReferenceCategories();
			dao.setReferenceCategories(categories.getResponse());
			eventBus.post(new Events.ReferenceCategoriesLoadedEvent());
		} catch (RetrofitError e) {
			eventBus.post(new Events.ReferenceCategoriesLoadErrorEvent());
		}
	}

	@ServiceAction
	void referencesAction(String slug) {
		try {
			ReferencesResponse data = api.getReferences(slug);
			dao.setReferences(data.getResponse().getEntities(), slug);
			eventBus.post(new Events.ReferencesLoadedEvent());
		} catch (RetrofitError e) {
			eventBus.post(new Events.ReferencesLoadErrorEvent());
		}
	}
}
