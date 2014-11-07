package ru.saransklife.event;

/**
 * Created by asavinova on 06/11/14.
 */
public class OpenEventEvent {

	private long id;

	public OpenEventEvent(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
