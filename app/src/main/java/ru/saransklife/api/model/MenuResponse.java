package ru.saransklife.api.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

import ru.saransklife.dao.MenuItem;

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
