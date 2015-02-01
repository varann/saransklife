package ru.saransklife.client.event;

import java.util.List;

import ru.saransklife.Response;

/**
 * Created by asavinova on 07/11/14.
 */
public class EventsResponse extends Response {

	private List<EventWrapper> response;

	public List<EventWrapper> getResponse() {
		return response;
	}

	public void setResponse(List<EventWrapper> response) {
		this.response = response;
	}
}
