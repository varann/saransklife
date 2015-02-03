package ru.saransklife.client.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;

/**
 * Created by asavinova on 03/02/15.
 */
@EViewGroup(R.layout.title_view)
public class TitleView extends LinearLayout {

	@ViewById TextView title;

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setTitle(String text) {
		title.setText(text);
	}

}
