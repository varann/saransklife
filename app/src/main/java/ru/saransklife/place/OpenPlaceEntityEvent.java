package ru.saransklife.place;

/**
 * Created by asavinova on 06/11/14.
 */
public class OpenPlaceEntityEvent {

	private long id;

	public OpenPlaceEntityEvent(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
