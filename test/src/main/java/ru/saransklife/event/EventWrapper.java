package ru.saransklife.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import ru.saransklife.place_entities.PlaceEntity;

/**
 * Created by asavinova on 07/11/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventWrapper extends Event {

	@JsonProperty(value = "Places")
	private List<PlaceEntity> places;

	public List<PlaceEntity> getPlaces() {
		return places;
	}

	public void setPlaces(List<PlaceEntity> places) {
		this.places = places;
	}

}
