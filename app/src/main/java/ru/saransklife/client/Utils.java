package ru.saransklife.client;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by asavinova on 07/11/14.
 */
public class Utils {

	public static final String IMAGE_BASE_URL = "http://images.pointresearch.ru";
	public static final String DEFAULT_IMAGE_SIZE = "?s=800x600";

	public static void displayImage(ImageView view, String path) {
		ImageLoader.getInstance().displayImage(Utils.IMAGE_BASE_URL + path + DEFAULT_IMAGE_SIZE, view);
	}
}
