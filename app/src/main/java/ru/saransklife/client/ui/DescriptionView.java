package ru.saransklife.client.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;

/**
 * Created by asavinova on 03/02/15.
 */
@EViewGroup(R.layout.description_view)
public class DescriptionView extends LinearLayout implements View.OnClickListener {

	@ViewById TextView text;
	@ViewById AwesomeIconTextView arrow;
	private String description;

	public DescriptionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnClickListener(this);
	}

	public void setText(String description) {
		this.description = description;
		text.setText(description);
	}

	@Override
	public void onClick(View v) {
		if (arrow.getText().equals(getResources().getString(R.string.angle_down))) {
			arrow.setText(R.string.angle_up);
			text.setMaxLines(100);
		} else {
			arrow.setText(R.string.angle_down);
			text.setMaxLines(2);
		}
		text.setText(description);
	}
}
