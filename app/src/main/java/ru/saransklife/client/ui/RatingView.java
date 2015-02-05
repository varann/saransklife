package ru.saransklife.client.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;

/**
 * Created by asavinova on 05/02/15.
 */
@EViewGroup(R.layout.rating_view)
public class RatingView extends LinearLayout {

	@ViewById TextView rating;

	public RatingView(Context context) {
		super(context);
	}

	public RatingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setRating(float rating) {
		this.rating.setText(String.valueOf(rating));
	}
}
