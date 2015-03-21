package ru.saransklife.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.saransklife.dao.Event;
import ru.saransklife.dao.EventCategory;
import ru.saransklife.dao.EventParams;
import ru.saransklife.dao.PlaceEntity;

/**
 * Created by asavinova on 07/11/14.
 */
public class ApiEvent extends Event {

	@SerializedName("Category")
	private EventCategory category;

	@SerializedName("Places")
	private List<PlaceEntity> places;

	@SerializedName("seances_data")
	private SeancesData seancesData;

	@SerializedName("additional_params")
	private ApiEventParams params;

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

	@Override
	public ApiEventParams getParams() {
		return params;
	}

	public void setParams(ApiEventParams params) {
		this.params = params;
	}
}
