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
@EViewGroup(R.layout.website_view)
public class WebsiteView extends LinearLayout implements View.OnClickListener {

	@ViewById TextView site;
	private String url;

	public WebsiteView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setUrl(String url) {
		this.url = url;

		setVisibility(TextUtils.isEmpty(url) ? GONE : VISIBLE);
		if (!TextUtils.isEmpty(url)) {
			setOnClickListener(this);

			SpannableString content = new SpannableString(url);
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			site.setText(content);
		}
	}


	@Override
	public void onClick(View v) {
		Utils.openLink(getContext(), url);
	}
}
