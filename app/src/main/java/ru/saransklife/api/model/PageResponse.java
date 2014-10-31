package ru.saransklife.api.model;

import ru.saransklife.dao.Page;

/**
 * Created by asavinova on 26/10/14.
 */
public class PageResponse extends Response {

	private Page response;

	public Page getResponse() {
		return response;
	}

	public void setResponse(Page response) {
		this.response = response;
	}
}
