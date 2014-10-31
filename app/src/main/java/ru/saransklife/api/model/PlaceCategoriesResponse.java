package ru.saransklife.api.model;

import java.util.List;

import ru.saransklife.dao.PlaceCategory;

/**
 * Created by asavinova on 31/10/14.
 */
public class PlaceCategoriesResponse extends Response {

	private List<PlaceCategory> response;

	public List<PlaceCategory> getResponse() {
		return response;
	}

	public void setResponse(List<PlaceCategory> response) {
		this.response = response;
	}
}
