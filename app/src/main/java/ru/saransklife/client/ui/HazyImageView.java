package ru.saransklife.client.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;

/**
 * Created by asavinova on 15/03/15.
 */
@EViewGroup(R.layout.hazy_image_view)
public class HazyImageView extends FrameLayout {

	@ViewById ImageView image;

	public HazyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageView getImage() {
		return image;
	}
}
