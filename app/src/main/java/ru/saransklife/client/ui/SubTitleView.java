package ru.saransklife.client.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;
import ru.saransklife.client.Utils;

/**
 * Created by asavinova on 03/02/15.
 */
@EViewGroup(R.layout.subtitle_view)
public class SubTitleView extends LinearLayout {

	@ViewById AwesomeIconTextView subtitle;

	public SubTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setTitle(int icon, String text) {
		Utils.setTextWithIcon(subtitle, icon, text);
	}

}
