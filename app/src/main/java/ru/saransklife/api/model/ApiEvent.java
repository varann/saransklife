package ru.saransklife.api.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import ru.saransklife.dao.Event;
import ru.saransklife.dao.PlaceEntity;

/**
 * Created by asavinova on 07/11/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiEvent extends Event {

	@JsonProperty(value = "Places")
	private List<PlaceEntity> places;

	public List<PlaceEntity> getPlaces() {
		return places;
	}

	public void setPlaces(List<PlaceEntity> places) {
		this.places = places;
	}

}
