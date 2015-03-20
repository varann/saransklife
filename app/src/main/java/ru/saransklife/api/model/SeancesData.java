package ru.saransklife.api.model;

import java.util.List;

import ru.saransklife.dao.Seance;

/**
 * Created by asavinova on 17/03/15.
 */
public class SeancesData {

	private List<Seance> seancesObjects;

	public List<Seance> getSeancesObjects() {
		return seancesObjects;
	}

	public void setSeancesObjects(List<Seance> seancesObjects) {
		this.seancesObjects = seancesObjects;
	}

}
