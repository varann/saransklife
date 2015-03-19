package ru.saransklife.client.event.info;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.saransklife.R;
import ru.saransklife.client.Utils;
import ru.saransklife.client.ui.AwesomeIconTextView;
import ru.saransklife.dao.PlaceEntity;
import ru.saransklife.dao.Seance;

/**
 * Created by asavinova on 18/03/15.
 */
@EViewGroup(R.layout.place_seances_view)
public class PlaceSeancesView extends LinearLayout {

	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	@ViewById TextView name;
	@ViewById AwesomeIconTextView address;
	@ViewById View delimeter;
	@ViewById GridLayout seancesGrid;

	public PlaceSeancesView(Context context) {
		super(context);
	}

	public PlaceSeancesView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setData(PlaceEntity placeEntity, List<Seance> seances, boolean showPlaceInfo) {
		name.setText(placeEntity.getName());
		Utils.setTextWithIcon(address, R.string.map_marker, placeEntity.getAddress());

		name.setVisibility(showPlaceInfo ? VISIBLE : GONE);
		address.setVisibility(showPlaceInfo ? VISIBLE : GONE);
		delimeter.setVisibility(showPlaceInfo ? VISIBLE : GONE);

		LayoutInflater inflater = LayoutInflater.from(getContext());
		seancesGrid.removeAllViews();
		seancesGrid.setColumnCount(calcColumnCount());

		Date date = new Date();
		for (Seance seance : seances) {
			View view = inflater.inflate(R.layout.seance_time_view, null);

			TextView time = (TextView) view.findViewById(R.id.time);
			TextView type = (TextView) view.findViewById(R.id.type);

			time.setText(timeFormat.format(seance.getDatetime()));
			type.setText(TextUtils.isEmpty(seance.getType()) ? "" : seance.getType());

			if (date.after(seance.getDatetime())) {
				time.setEnabled(false);
			}

			seancesGrid.addView(view);
		}
	}

	private int calcColumnCount() {
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int displayWidth = size.x;
		Resources r = getResources();
		int cellWidth = r.getDimensionPixelSize(R.dimen.seance_time_width);
		return displayWidth / cellWidth;
	}
}
