package ru.saransklife.client.place;

import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;
import ru.saransklife.R;
import ru.saransklife.client.DetailsActivity;
import ru.saransklife.client.DetailsActivity_;
import ru.saransklife.client.Utils;
import ru.saransklife.client.ui.AwesomeIconTextView;
import ru.saransklife.client.ui.DescriptionView;
import ru.saransklife.client.ui.DetailsButton;
import ru.saransklife.client.ui.ItemRecommendedInfoView;
import ru.saransklife.client.ui.RatingView;
import ru.saransklife.client.ui.TitleView;
import ru.saransklife.dao.PlaceEntity;

/**
 * A simple {@link Fragment} subclass.
 */
@EActivity(R.layout.activity_entity)
public class EntityActivity extends BaseActivity {

	@ViewById Toolbar toolbar;
	@ViewById ImageView photo;
	@ViewById RatingView ratingView;
	@ViewById ItemRecommendedInfoView recommendedInfo;
	@ViewById DetailsButton detailsButton;
	@ViewById TextView photoAuthor;
	@ViewById TitleView titleView;
	@ViewById DescriptionView descriptionView;
	@ViewById AwesomeIconTextView addressView;
	@ViewById AwesomeIconTextView phoneView;
	@ViewById AwesomeIconTextView emailView;
	@ViewById AwesomeIconTextView websiteView;

	@Bean Dao dao;
	@Extra long id;
	private PlaceEntity entity;

	@AfterViews
	void afterViews() {
		logExtra(new String[]{"id"}, Long.toString(id));

		entity = dao.getPlaceEntity(id);
		toolbar.setTitle(entity.getName());
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (entity.getPhoto_path() != null) {
			Utils.displayImage(photo, entity.getPhoto_path());
		}

		ratingView.setRating(entity.getRating());
		recommendedInfo.setInfo(entity.getView_count(), entity.getRecommended_count());

		titleView.setTitle(entity.getName());
		descriptionView.setText(entity.getDescription());

		detailsButton.setVisibility(TextUtils.isEmpty(entity.getInformation()) ? View.GONE : View.VISIBLE);

		setTextWithIcon(photoAuthor, R.string.photo_author, entity.getPhoto_author());
		setTextWithIcon(addressView, R.string.map_marker, entity.getAddress());
		setTextWithIcon(phoneView, R.string.phone, entity.getPhone());
		setTextWithIcon(emailView, R.string.envelope, entity.getEmail());
		setTextWithIcon(websiteView, R.string.globe, entity.getWebsite());
	}

	private void setTextWithIcon(TextView view, int icon, String text) {
		view.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		view.setText(TextUtils.isEmpty(text) ? "" : getString(icon) + " " + text);
	}

	@Click
	void detailsButtonClicked() {
		DetailsActivity_.intent(this)
				.id(id)
				.text(entity.getInformation())
				.from(DetailsActivity.PLACE)
				.start();
	}

	@OptionsItem
	void homeSelected() {
		NavUtils.navigateUpFromSameTask(this);
	}
}
