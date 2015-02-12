package ru.saransklife.client.place;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.PostResponse;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;
import ru.saransklife.client.DetailsActivity;
import ru.saransklife.client.DetailsActivity_;
import ru.saransklife.client.Utils;
import ru.saransklife.client.ui.AwesomeIconTextView;
import ru.saransklife.client.ui.DescriptionView;
import ru.saransklife.client.ui.DetailsButton;
import ru.saransklife.client.ui.ItemRecommendedInfoView;
import ru.saransklife.client.ui.RatingView;
import ru.saransklife.client.ui.SetRatingAlertDialog;
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
	@ViewById CardView setRecommended;
	@ViewById CardView setRating;
	@ViewById ProgressBar progress;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;
	@Extra long id;
	@Extra boolean isInteresting;

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

		setPlaceView();
	}

	@Background
	void setPlaceView() {
		apiClient.setPlaceView(id, getDeviceId());
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

	@Click
	void setRecommendedClicked() {
		progress.setVisibility(View.VISIBLE);
		setPlaceRecommended();
	}

	@Background
	void setPlaceRecommended() {
		PostResponse result = apiClient.setPlaceRecommended(id, getDeviceId());
		recommendedUpdate(result.isResult() ? getString(R.string.recommendation_setted) : result.getError());
	}

	@UiThread
	void recommendedUpdate(String message) {
		progress.setVisibility(View.GONE);
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	@Click
	void setRatingClicked() {
		final SetRatingAlertDialog ratingDialog = new SetRatingAlertDialog(this);
		ratingDialog.setPositiveButton(R.string.dialog_ок, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				progress.setVisibility(View.VISIBLE);
				setPlaceRating(ratingDialog.getSelectedRating());
			}
		});
		ratingDialog.create().show();
	}

	@Background
	void setPlaceRating(int rating) {
		PostResponse result = apiClient.setPlaceRating(id, getDeviceId(), rating);
		ratingUpdate(result.isResult() ? getString(R.string.rating_setted) : result.getError());
	}

	@UiThread
	void ratingUpdate(String message) {
		progress.setVisibility(View.GONE);
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}

	@OptionsItem
	void homeSelected() {
		if (isInteresting) {
			PlaceCategoriesActivity_.intent(this).start();
		} else {
			NavUtils.navigateUpFromSameTask(this);
		}
	}
}
