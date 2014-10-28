package ru.saransklife.api.model;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

import ru.saransklife.dao.MenuItem;

/**
 * Created by asavinova on 27/10/14.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ApiMenuItem extends MenuItem {

	private List<ApiMenuItem> child;

	public List<ApiMenuItem> getChild() {
		return child;
	}

	public void setChild(List<ApiMenuItem> child) {
		this.child = child;
	}

}
