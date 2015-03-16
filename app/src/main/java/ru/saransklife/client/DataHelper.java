package ru.saransklife.client;

import android.content.Context;
import android.database.Cursor;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Date;
import java.util.List;

import ru.saransklife.dao.PlaceCategory;
import ru.saransklife.dao.Reference;
import ru.saransklife.dao.ReferenceCategory;

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

	public List<PlaceCategory> getSubPlaceCategories(String slug, Context context) {
		List<PlaceCategory> data = dao.getSubPlaceCategories(slug);

		if (needUpdate(Dao.Request.PLACE_CATEGORIES, slug)) {
			eventBus.post(new Events.SubPlaceCategoriesStartLoadingEvent());
			DataService_.intent(context).subPlaceCategoriesAction(slug).start();
		}

		return data;
	}

	public Cursor getPlaceEntitiesCursor(String slug, boolean force, Context context) {
		Cursor cursor = dao.getPlaceEntitiesBySlugCursor(slug);

		if (force || needUpdate(Dao.Request.PLACE_ENTITIES, slug)) {
			eventBus.post(new Events.PlaceEntitiesStartLoadingEvent());
			DataService_.intent(context).placeEntitiesAction(slug).start();
		}

		return cursor;
	}

	public Cursor getEventCategoriesCursor(String type, boolean force, Context context) {
		Cursor cursor = dao.getEventCategories(type);

		if (force || needUpdate(Dao.Request.EVENTS, type)) {
			eventBus.post(new Events.EventsStartLoadingEvent());
			DataService_.intent(context).eventsAction(type).start();
		}

		return cursor;
	}

	public List<ReferenceCategory> getReferenceCategories(boolean force, Context context) {
		List<ReferenceCategory> data = dao.getReferenceCategories();

		if (force || needUpdate(Dao.Request.REFERENCE_CATEGORIES, null)) {
			eventBus.post(new Events.ReferenceCategoriesStartLoadingEvent());
			DataService_.intent(context).referenceCategoriesAction().start();
		}

		return data;
	}

	public List<Reference> getReferences(String slug, boolean force, Context context) {
		List<Reference> data = dao.getReferences(slug);

		if (force || needUpdate(Dao.Request.REFERENCES, slug)) {
			eventBus.post(new Events.ReferencesStartLoadingEvent());
			DataService_.intent(context).referencesAction(slug).start();
		}

		return data;
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
