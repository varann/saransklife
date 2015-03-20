package ru.saransklife.api.model;

import java.util.List;

import ru.saransklife.dao.SectionItem;

/**
 * Created by asavinova on 27/10/14.
 */
public class ApiSectionItem extends SectionItem {

	private List<ApiSectionItem> child;

	public List<ApiSectionItem> getChild() {
		return child;
	}

	public void setChild(List<ApiSectionItem> child) {
		this.child = child;
	}

}
