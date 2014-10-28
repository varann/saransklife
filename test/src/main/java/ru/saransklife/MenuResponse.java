package ru.saransklife;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by asavinova on 26/10/14.
 */
public class MenuResponse extends Response {

	private List<ApiMenuItem> response;

	public List<ApiMenuItem> getResponse() {
		return response;
	}

	public void setResponse(List<ApiMenuItem> response) {
		this.response = response;
	}
}
