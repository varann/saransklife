package ru.saransklife.place;

/**
 * Created by asavinova on 06/11/14.
 */
public class OpenPlaceEntitiesEvent {

	private long id;

	public OpenPlaceEntitiesEvent(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
