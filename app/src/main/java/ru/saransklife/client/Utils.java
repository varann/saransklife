package ru.saransklife.client;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by asavinova on 07/11/14.
 */
public class Utils {

	private static Logger L = LoggerFactory.getLogger(Utils.class);

	public static final String IMAGE_BASE_URL = "http://images.pointresearch.ru";
	public static final String DEFAULT_IMAGE_SIZE = "?s=800x600";

	public static void displayImage(ImageView view, String path) {
		String url = Utils.IMAGE_BASE_URL + path + DEFAULT_IMAGE_SIZE;
		Picasso.with(view.getContext()).load(url).into(view);
	}

	public static void displayImage(ImageView view, String path, Integer widthDP, Integer heightDP) {
		int widthPX = 0;
		int heightPX = 0;
		WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		if (widthDP == null) {
			widthPX = size.x;
		} else {
			Resources r = view.getResources();
			widthPX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthDP, r.getDisplayMetrics());
		}
		if (heightDP == null) {
			heightPX = size.y;
		} else {
			Resources r = view.getResources();
			heightPX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDP, r.getDisplayMetrics());
		}

		String url = Utils.IMAGE_BASE_URL + path + "?s=" + widthPX + "x" + heightPX;
		Picasso.with(view.getContext()).load(url).into(view);
	}
}
