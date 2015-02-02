package ru.saransklife.client.event;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
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
import ru.saransklife.dao.EventCategory;


@EActivity(R.layout.activity_event_info)
public class EventInfoActivity extends ActionBarActivity {

	@ViewById Toolbar toolbar;
	@ViewById ImageView photo;

	@ViewById TextView categoryName;
	@ViewById TextView name;
	@ViewById TextView description;

	@Bean Dao dao;

	@Extra long id;

	@AfterViews
	void afterViews() {
		Event event = dao.getEventById(id);

		toolbar.setTitle(event.getName());
		toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
		setSupportActionBar(toolbar);

		if (event.getPhoto_path() != null) {
			Utils.displayImage(photo, event.getPhoto_path());
		}

		EventCategory category = dao.getEventCategoryById(event.getCategory_id());
		setText(categoryName, category.getName());
		setText(name, event.getName());
		setText(description, event.getDescription());
	}

	private void setText(TextView view, String text) {
		view.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		view.setText(TextUtils.isEmpty(text) ? "" : text);
	}

}
