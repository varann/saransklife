package ru.saransklife.client.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import org.androidannotations.annotations.EView;

import ru.saransklife.R;

/**
 * Created by asavinova on 03/02/15.
 */
@EView
public class AwesomeIconTextView extends TextView {

	public AwesomeIconTextView(Context context) {
		super(context);
		init();
	}

	public AwesomeIconTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AwesomeIconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fontawesome.ttf");
		setTypeface(typeface);

		setShadowLayer(1, 1, 1, R.color.shadow);
	}

}
