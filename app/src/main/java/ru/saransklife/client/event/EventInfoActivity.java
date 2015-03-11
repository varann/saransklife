package ru.saransklife.client.event;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;
import ru.saransklife.client.DetailsActivity;
import ru.saransklife.client.DetailsActivity_;
import ru.saransklife.client.Utils;
import ru.saransklife.client.ui.DescriptionView;
import ru.saransklife.client.ui.DetailsButton;
import ru.saransklife.client.ui.TitleView;
import ru.saransklife.dao.Event;
import ru.saransklife.dao.EventCategory;


@EActivity(R.layout.activity_event_info)
public class EventInfoActivity extends BaseActivity {

	@ViewById Toolbar toolbar;
	@ViewById ImageView photo;
	@ViewById DetailsButton detailsButton;
	@ViewById TextView categoryName;
	@ViewById TitleView titleView;
	@ViewById DescriptionView descriptionView;
	@ViewById TextView message;
	@ViewById LinearLayout eventLayout;

	@Bean Dao dao;
	@Extra long id;

	private Event event;

	@AfterViews
	void afterViews() {
		logExtra(new String[]{"id"}, Long.toString(id));

		toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
		setSupportActionBar(toolbar);

		event = dao.getEventById(id);

		message.setVisibility(event == null ? View.VISIBLE : View.GONE);
		eventLayout.setVisibility(event == null ? View.GONE : View.VISIBLE);

		if (event != null) {
			toolbar.setTitle(event.getName());

			if (event.getPhoto_path() != null) {
				Utils.displayImage(photo, event.getPhoto_path());
			}

			detailsButton.setVisibility(TextUtils.isEmpty(event.getStory()) ? View.GONE : View.VISIBLE);

			EventCategory category = dao.getEventCategoryById(event.getCategory_id());
			setText(categoryName, category.getName());
			titleView.setTitle(event.getName());
			descriptionView.setText(event.getDescription());
		}
	}

	private void setText(TextView view, String text) {
		view.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		view.setText(TextUtils.isEmpty(text) ? "" : text);
	}

	@Click
	void detailsButtonClicked() {
		DetailsActivity_.intent(this)
				.id(id)
				.text(event.getStory())
				.from(DetailsActivity.EVENT)
				.start();
	}
}
