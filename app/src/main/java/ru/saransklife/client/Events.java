package ru.saransklife.client;

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

	public class MenuLoadedEvent {
	}

	public class MenuLoadErrorEvent {
	}

}
