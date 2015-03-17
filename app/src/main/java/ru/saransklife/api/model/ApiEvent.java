package ru.saransklife.api.model;

import com.google.gson.annotations.SerializedName;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

import ru.saransklife.dao.Event;
import ru.saransklife.dao.EventCategory;
import ru.saransklife.dao.PlaceEntity;

/**
 * Created by asavinova on 07/11/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiEvent extends Event {

	@JsonProperty(value = "Category")
	@SerializedName("Category")
	private EventCategory category;

	@JsonProperty(value = "Places")
	@SerializedName("Places")
	private List<PlaceEntity> places;

	@JsonProperty(value = "seances_data")
	@SerializedName("seances_data")
	private SeancesData seancesData;

	public List<PlaceEntity> getPlaces() {
		return places;
	}

	public void setPlaces(List<PlaceEntity> places) {
		this.places = places;
	}

	public EventCategory getCategory() {
		return category;
	}

	public void setCategory(EventCategory category) {
		this.category = category;
	}

	public SeancesData getSeancesData() {
		return seancesData;
	}

	public void setSeancesData(SeancesData seancesData) {
		this.seancesData = seancesData;
	}

}
