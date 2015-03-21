package ru.saransklife.client.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

/**
 * Created by asavinova on 03/02/15.
 */
@EViewGroup(R.layout.phone_view)
public class PhoneView extends LinearLayout implements View.OnClickListener {

	@ViewById TextView phone;
	private String phoneNumber;

	public PhoneView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;

		setVisibility(TextUtils.isEmpty(phoneNumber) ? GONE : VISIBLE);
		if (!TextUtils.isEmpty(phoneNumber)) {
			setOnClickListener(this);

			SpannableString content = new SpannableString(phoneNumber);
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			phone.setText(content);
		}
	}


	@Override
	public void onClick(View v) {
		String uri = "tel:" + phoneNumber;
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse(uri));
		getContext().startActivity(intent);
	}
}
