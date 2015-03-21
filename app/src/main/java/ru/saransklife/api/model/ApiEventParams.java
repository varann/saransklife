package ru.saransklife.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.saransklife.dao.EventParams;

/**
 * Created by asavinova on 21/03/15.
 */
public class ApiEventParams extends EventParams {

	@SerializedName("images")
	private List<String> imagesArray;

	public List<String> getImagesArray() {
		return imagesArray;
	}

	public void setImagesArray(List<String> imagesArray) {
		this.imagesArray = imagesArray;
	}
}
