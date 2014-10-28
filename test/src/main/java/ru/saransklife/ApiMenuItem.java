package ru.saransklife;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asavinova on 27/10/14.
 */
public class ApiMenuItem {

	private Long id;
	private String name;
	private String slug;
	private String module;

	private List<ApiMenuItem> child;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public List<ApiMenuItem> getChild() {
		return child;
	}

	public void setChild(List<ApiMenuItem> child) {
		this.child = child;
	}

//	public ApiMenuItem getParent() {
//		return parent;
//	}
//
//	public void setParent(ApiMenuItem parent) {
//		this.parent = parent;
//	}
}
