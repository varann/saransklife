package ru.saransklife.client.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import org.androidannotations.annotations.EView;

import ru.saransklife.R;
import ru.saransklife.client.Utils;
import ru.saransklife.dao.Event;

/**
 * Created by asavinova on 05/02/15.
 */
@EView
public class NearestSeanceView extends AwesomeIconTextView {

	public NearestSeanceView(Context context) {
		super(context, null);
	}

	public NearestSeanceView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		setSingleLine(true);
		setEllipsize(TextUtils.TruncateAt.END);
		setTextColor(getResources().getColor(R.color.white));
		setShadowLayer(1, 1, 1, R.color.shadow);
	}

	public void updateSeanceInfo(Event event) {
		String nearestSeance = Utils.getNearestSeance(event);
		if (nearestSeance == null) {
			setVisibility(GONE);
		} else {
			setVisibility(VISIBLE);
			Utils.setTextWithIcon(this, R.string.calendar, nearestSeance);
		}
	}
}
