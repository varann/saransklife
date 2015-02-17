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

	public PageLoadErrorEvent getPageLoadErrorEvent() {
		return new PageLoadErrorEvent();
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
	}

}
