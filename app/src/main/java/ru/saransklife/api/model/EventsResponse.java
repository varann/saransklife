package ru.saransklife.api.model;

import java.util.List;

/**
 * Created by asavinova on 07/11/14.
 */
public class EventsResponse extends Response {

	private List<ApiEvent> response;

	public List<ApiEvent> getResponse() {
		return response;
	}

	public void setResponse(List<ApiEvent> response) {
		this.response = response;
	}
}
