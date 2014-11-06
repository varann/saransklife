package ru.saransklife.place_entities;

import java.util.List;

import ru.saransklife.Response;

/**
 * Created by asavinova on 05/11/14.
 */
public class PlaceEntitiesResponse extends Response {

	private PlaceEntities response;

	public PlaceEntities getResponse() {
		return response;
	}

	public void setResponse(PlaceEntities response) {
		this.response = response;
	}

	public class PlaceEntities {
		private Meta meta;
		private List<PlaceEntity> entities;

		public Meta getMeta() {
			return meta;
		}

		public void setMeta(Meta meta) {
			this.meta = meta;
		}

		public List<PlaceEntity> getEntities() {
			return entities;
		}

		public void setEntities(List<PlaceEntity> entities) {
			this.entities = entities;
		}
	}

}
