package ru.saransklife.client.ui;

import android.content.Context;
import android.util.AttributeSet;

import ru.saransklife.R;

/**
 * Created by asavinova on 05/02/15.
 */
public class ItemRecommendedInfoView extends AwesomeIconTextView {

	public ItemRecommendedInfoView(Context context) {
		super(context);
	}

	public ItemRecommendedInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setInfo(int viewCount, int recommendedCount) {
		String markerView = getResources().getString(R.string.eye);
		String markerRecommended = getResources().getString(R.string.thumbs_up);
		setText(markerView + " " + viewCount + "   " + markerRecommended + " " + recommendedCount);
	}
}
