package ru.saransklife.client.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import ru.saransklife.R;

/**
 * Created by asavinova on 05/02/15.
 */
public class ItemNameView extends TextView {

	public ItemNameView(Context context) {
		super(context, null);
	}

	public ItemNameView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		setMaxLines(2);
		setEllipsize(TextUtils.TruncateAt.END);
		setTextColor(getResources().getColor(R.color.white));
		setShadowLayer(1, 1, 1, R.color.shadow);
	}
}
