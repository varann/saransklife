package ru.saransklife.client.event;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.client.Dao;
import ru.saransklife.R;
import ru.saransklife.client.Utils;
import ru.saransklife.dao.Event;


@EActivity(R.layout.activity_event_info)
public class EventInfoActivity extends Activity {

	@ViewById ImageView photo;

	@ViewById TextView
			name,
			description;

	@Bean Dao dao;

	@Extra long id;

	@AfterViews
	void afterViews() {
		Event event = dao.getEventById(id);

		if (event.getPhoto_path() != null) {
			Utils.displayImage(photo, event.getPhoto_path());
		}

		setText(name, event.getName());
		setText(description, event.getDescription());
	}

	private void setText(TextView view, String text) {
		view.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		view.setText(TextUtils.isEmpty(text) ? "" : text);
	}

}
