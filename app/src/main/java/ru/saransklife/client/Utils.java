package ru.saransklife.client;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.saransklife.R;

/**
 * Created by asavinova on 07/11/14.
 */
public class Utils {

	private static Logger L = LoggerFactory.getLogger(Utils.class);

	public static final String IMAGE_BASE_URL = "http://images.pointresearch.ru";

	public static void displayImage(ImageView view, String path, Integer widthDP, Integer heightDP) {
		String url = null;
		if (!TextUtils.isEmpty(path)) {
			url = getUrl(view, path, widthDP, heightDP);
		}

		Picasso.with(view.getContext())
				.load(url)
				.placeholder(R.drawable.bg_nophoto)
				.error(R.drawable.bg_nophoto)
				.into(view);
	}

	private static String getUrl(ImageView view, String path, Integer widthDP, Integer heightDP) {
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

		return Utils.IMAGE_BASE_URL + path + "?s=" + widthPX + "x" + heightPX;
	}
}
