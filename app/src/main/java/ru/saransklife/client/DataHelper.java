package ru.saransklife.client;

import android.content.Context;
import android.database.Cursor;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Date;

/**
 * Created by asavinova on 19/02/15.
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataHelper {

	@Bean Dao dao;
	@Bean EventBus eventBus;

	public Cursor getInterestingPlacesCursor(boolean force, Context context) {
		Cursor cursor = dao.getPlaceEntitiesBySlugCursor(Dao.INTERESTING_PLACES_SLUG);

		if (force || needUpdate(Dao.Request.INTERESTING_PLACES, Dao.INTERESTING_PLACES_SLUG)) {
			DataService_.intent(context).interestingPlacesAction().start();
		}

		return cursor;
	}

	public Cursor getPlaceCategoriesCursor(boolean force, Context context) {
		Cursor cursor = dao.getPlaceCategoryCursor();

		if (force || needUpdate(Dao.Request.PLACE_CATEGORIES, null)) {
			eventBus.post(new Events.PlaceCategoriesStartLoadingEvent());
			DataService_.intent(context).placeCategoriesAction().start();
		}

		return cursor;
	}

	public Cursor getPlaceEntitiesCursor(String slug, boolean force, Context context) {
		Cursor cursor = dao.getPlaceEntitiesBySlugCursor(slug);

		if (force || needUpdate(Dao.Request.PLACE_ENTITIES, slug)) {
			eventBus.post(new Events.PlaceEntitiesStartLoadingEvent());
			DataService_.intent(context).placeEntitiesAction(slug).start();
		}

		return cursor;
	}

	public Cursor getEventCategoriesCursor(boolean force, Context context) {
		Cursor cursor = dao.getEventCategories();

		if (force || needUpdate(Dao.Request.EVENTS_AND_CATEGORIES, null)) {
			eventBus.post(new Events.EventsAndCategoriesStartLoadingEvent());
			DataService_.intent(context).eventsAndCategoriesAction().start();
		}

		return cursor;
	}

	private boolean needUpdate(Dao.Request request, String params) {
		Date lastUpdated = dao.getLastUpdated(request, params);

		if (lastUpdated == null) return true;

		Date current = new Date();
		long minutes = (current.getTime() - lastUpdated.getTime()) / 1000 / 60;
		if (minutes < 60) {
			return false;
		} else {
			return true;
		}
	}
}
