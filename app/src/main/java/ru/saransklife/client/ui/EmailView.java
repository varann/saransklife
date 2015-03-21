package ru.saransklife.client.ui;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;
import ru.saransklife.client.Utils;

/**
 * Created by asavinova on 03/02/15.
 */
@EViewGroup(R.layout.email_view)
public class EmailView extends LinearLayout implements View.OnClickListener {

	@ViewById TextView emailView;
	private String email;

	public EmailView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setEmail(String email) {
		this.email = email;

		setVisibility(TextUtils.isEmpty(email) ? GONE : VISIBLE);
		if (!TextUtils.isEmpty(email)) {
			setOnClickListener(this);

			SpannableString content = new SpannableString(email);
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			emailView.setText(content);
		}
	}


	@Override
	public void onClick(View v) {
		Utils.mailTo(getContext(), "mailto:" + email, "");
	}
}
