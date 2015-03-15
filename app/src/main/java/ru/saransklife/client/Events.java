package ru.saransklife.client;

import ru.saransklife.dao.Page;

/**
 * Created by asavinova on 13/02/15.
 */
public class Events {

	public static class MenuLoadedEvent {
	}

	public static class MenuLoadErrorEvent {
	}


	public static class PageLoadedEvent {
		private Page page;

		public PageLoadedEvent(Page page) {
			this.page = page;
		}

		public Page getPage() {
			return page;
		}
	}

	public static class PageLoadErrorEvent {
		private String slug;

		public PageLoadErrorEvent(String slug) {
			this.slug = slug;
		}

		public String getSlug() {
			return slug;
		}
	}


	public static class InterestingPlacesLoadedEvent {
	}

	public static class InterestingPlacesLoadErrorEvent {
	}


	public static class PlaceCategoriesStartLoadingEvent {
	}

	public static class PlaceCategoriesLoadedEvent {
	}

	public static class PlaceCategoriesLoadErrorEvent {
	}

	public static class SubPlaceCategoriesStartLoadingEvent {
	}

	public static class SubPlaceCategoriesLoadedEvent {
	}

	public static class SubPlaceCategoriesLoadErrorEvent {
	}

	public static class PlaceEntitiesStartLoadingEvent {
	}

	public static class PlaceEntitiesLoadedEvent {
	}

	public static class PlaceEntitiesLoadErrorEvent {
	}


	public static class EventsAndCategoriesStartLoadingEvent {
	}

	public static class EventsAndCategoriesLoadedEvent {
	}

	public static class EventsAndCategoriesLoadErrorEvent {
	}


	public static class ReferenceCategoriesStartLoadingEvent {
	}

	public static class ReferenceCategoriesLoadedEvent {
	}

	public static class ReferenceCategoriesLoadErrorEvent {
	}


	public static class ReferencesStartLoadingEvent {
	}

	public static class ReferencesLoadedEvent {
	}

	public static class ReferencesLoadErrorEvent {
	}

}
