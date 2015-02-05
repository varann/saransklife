package ru.saransklife.client.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

import ru.saransklife.R;

/**
 * Created by asavinova on 05/02/15.
 */
public class DetailsButton extends AwesomeIconTextView {

	public DetailsButton(Context context) {
		super(context, null);
	}

	public DetailsButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		setTextColor(getResources().getColor(R.color.white));
		setText(R.string.info);
		setTextSize(TypedValue.COMPLEX_UNIT_SP, 48);
		setShadowLayer(1, 1, 1, R.color.shadow);
	}
}
