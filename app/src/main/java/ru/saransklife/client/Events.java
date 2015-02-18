package ru.saransklife.client;

import ru.saransklife.dao.Page;

/**
 * Created by asavinova on 13/02/15.
 */
public class Events {

	public MenuLoadedEvent getMenuLoadedEvent() {
		return new MenuLoadedEvent();
	}

	public MenuLoadErrorEvent getMenuLoadErrorEvent() {
		return new MenuLoadErrorEvent();
	}

	public PageLoadedEvent getPageLoadedEvent(Page page) {
		return new PageLoadedEvent(page);
	}

	public PageLoadErrorEvent getPageLoadErrorEvent(String slug) {
		return new PageLoadErrorEvent(slug);
	}

	public InterestingPlacesLoadedEvent getInterestingPlacesLoadedEvent() {
		return new InterestingPlacesLoadedEvent();
	}

	public InterestingPlacesLoadErrorEvent getInterestingPlacesLoadErrorEvent() {
		return new InterestingPlacesLoadErrorEvent();
	}

	public PlaceCategoriesLoadedEvent getPlaceCategoriesLoadedEvent() {
		return new PlaceCategoriesLoadedEvent();
	}

	public PlaceCategoriesLoadErrorEvent getPlaceCategoriesLoadErrorEvent() {
		return new PlaceCategoriesLoadErrorEvent();
	}


	public class MenuLoadedEvent {
	}

	public class MenuLoadErrorEvent {
	}


	public class PageLoadedEvent {
		private Page page;

		public PageLoadedEvent(Page page) {
			this.page = page;
		}

		public Page getPage() {
			return page;
		}
	}

	public class PageLoadErrorEvent {
		private String slug;

		public PageLoadErrorEvent(String slug) {
			this.slug = slug;
		}

		public String getSlug() {
			return slug;
		}
	}

	public class InterestingPlacesLoadedEvent {
	}

	public class InterestingPlacesLoadErrorEvent {
	}


	public class PlaceCategoriesLoadedEvent {
	}

	public class PlaceCategoriesLoadErrorEvent {
	}

}
