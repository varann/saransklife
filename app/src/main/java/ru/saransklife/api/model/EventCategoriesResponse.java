package ru.saransklife.api.model;

import java.util.List;

import ru.saransklife.dao.EventCategory;

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
