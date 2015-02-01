package ru.saransklife.client.event;

import java.util.List;

import ru.saransklife.Response;

/**
 * Created by asavinova on 07/11/14.
 */
public class EventCategoriesResponse extends Response {

	private List<EventCategory> response;

	public List<EventCategory> getResponse() {
		return response;
	}

	public void setResponse(List<EventCategory> response) {
		this.response = response;
	}

}
