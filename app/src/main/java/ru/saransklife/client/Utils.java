package ru.saransklife.client;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.saransklife.R;
import ru.saransklife.client.ui.HazyImageView;
import ru.saransklife.dao.Event;
import ru.saransklife.dao.Seance;

/**
 * Created by asavinova on 07/11/14.
 */
public class Utils {

	private static Logger L = LoggerFactory.getLogger(Utils.class);

	public static final String IMAGE_BASE_URL = "http://images.pointresearch.ru";

	private static final String DATE_FORMAT = "EEEE, dd MMMM HH:mm";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

	public static void displayImage(HazyImageView view, String path, Integer widthDP, Integer heightDP) {
		displayImage(view.getImage(), path, widthDP, heightDP);
	}

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

	public static void setTextWithIcon(TextView view, int icon, String text) {
		view.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		view.setText(TextUtils.isEmpty(text) ? "" : view.getResources().getString(icon) + " " + text);
	}

	public static String getNearestSeance(Event event) {
		List<Seance> seances = event.getSeances();
		Date currentDate = new Date();
		for (Seance seance : seances) {
			Date date = seance.getDatetime();
			if (currentDate.before(date)) {
				return StringUtils.capitalize(dateFormat.format(date));
			}
		}

		return null;
	}
}
