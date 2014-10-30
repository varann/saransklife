package ru.saransklife.api.model;

import java.util.List;

/**
 * Created by asavinova on 26/10/14.
 */
public class MenuResponse extends Response {

	private List<ApiSectionItem> response;

	public List<ApiSectionItem> getResponse() {
		return response;
	}

	public void setResponse(List<ApiSectionItem> response) {
		this.response = response;
	}

}
