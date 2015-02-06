package ru.saransklife.api.model;

import java.util.List;

import ru.saransklife.dao.ReferenceCategory;

/**
 * Created by asavinova on 07/11/14.
 */
public class ReferenceCategoriesResponse extends Response {

	private List<ReferenceCategory> response;

	public List<ReferenceCategory> getResponse() {
		return response;
	}

	public void setResponse(List<ReferenceCategory> response) {
		this.response = response;
	}
}
