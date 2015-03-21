package ru.saransklife.client.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import org.androidannotations.annotations.EView;

import ru.saransklife.R;

/**
 * Created by asavinova on 05/02/15.
 */
@EView
public class WhiteTextWithShadowView extends AwesomeIconTextView {

	public WhiteTextWithShadowView(Context context) {
		super(context, null);
	}

	public WhiteTextWithShadowView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setSingleLine(true);
		setEllipsize(TextUtils.TruncateAt.END);
		setTextColor(getResources().getColor(R.color.white));
		setShadowLayer(1, 1, 1, R.color.shadow);
	}

}
