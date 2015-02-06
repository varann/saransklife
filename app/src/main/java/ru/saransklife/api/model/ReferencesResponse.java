package ru.saransklife.api.model;

import java.util.List;

import ru.saransklife.dao.Reference;

/**
 * Created by asavinova on 07/11/14.
 */
public class ReferencesResponse extends Response {

	private ReferenceEntities response;

	public ReferenceEntities getResponse() {
		return response;
	}

	public void setResponse(ReferenceEntities response) {
		this.response = response;
	}

	public class ReferenceEntities {
		private Meta meta;
		private List<Reference> entities;

		public Meta getMeta() {
			return meta;
		}

		public void setMeta(Meta meta) {
			this.meta = meta;
		}

		public List<Reference> getEntities() {
			return entities;
		}

		public void setEntities(List<Reference> entities) {
			this.entities = entities;
		}
	}
}
