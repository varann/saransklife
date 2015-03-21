package ru.saransklife.client.place;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;
import ru.saransklife.api.ApiCLient;
import ru.saransklife.api.model.PostResponse;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;
import ru.saransklife.client.DetailsActivity_;
import ru.saransklife.client.Utils;
import ru.saransklife.client.ui.AwesomeIconTextView;
import ru.saransklife.client.ui.DescriptionView;
import ru.saransklife.client.ui.DetailsButton;
import ru.saransklife.client.ui.GoogleMapLayout;
import ru.saransklife.client.ui.HazyImageView;
import ru.saransklife.client.ui.ItemRecommendedInfoView;
import ru.saransklife.client.ui.RatingView;
import ru.saransklife.client.ui.SetRatingAlertDialog;
import ru.saransklife.client.ui.TitleView;
import ru.saransklife.client.ui.WebsiteView;
import ru.saransklife.dao.PlaceEntity;

/**
 * A simple {@link Fragment} subclass.
 */
@EActivity(R.layout.activity_entity)
public class EntityActivity extends BaseActivity implements OnMapReadyCallback {

	private static final int PLACE_VIEW_LOADER_ID = 0;
	private static final int PLACE_RECOMMENDED_LOADER_ID = 1;
	private static final int PLACE_RATING_LOADER_ID = 2;

	private static final String SELECTED_RATING = "rating";

	@ViewById Toolbar toolbar;
	@ViewById HazyImageView photo;
	@ViewById RatingView ratingView;
	@ViewById ItemRecommendedInfoView recommendedInfo;
	@ViewById DetailsButton detailsButton;
	@ViewById TextView photoAuthor;
	@ViewById TitleView titleView;
	@ViewById DescriptionView descriptionView;
	@ViewById GoogleMapLayout mapLayout;
	@ViewById AwesomeIconTextView addressView;
	@ViewById AwesomeIconTextView phoneView;
	@ViewById AwesomeIconTextView emailView;
	@ViewById WebsiteView websiteView;
	@ViewById CardView setRecommended;
	@ViewById CardView setRating;
	@ViewById ProgressBar progress;
	@ViewById ScrollView scroll;

	@Bean Dao dao;
	@Bean ApiCLient api;

	@Extra long id;
	@Extra boolean isInteresting;

	private PlaceEntity entity;

	@AfterViews
	void afterViews() {
		logExtra(new String[]{"id", "interesting"}, Long.toString(id), String.valueOf(isInteresting));

		entity = dao.getPlaceEntity(id);
		toolbar.setTitle(entity.getName());
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Utils.displayImage(photo, entity.getPhoto_path(), null, 200);

		ratingView.setRating(entity.getRating());
		recommendedInfo.setInfo(entity.getView_count(), entity.getRecommended_count());

		titleView.setTitle(entity.getName());
		descriptionView.setText(entity.getDescription());

		mapLayout.setListener(new GoogleMapLayout.OnTouchListener() {
			@Override
			public void onTouch() {
				scroll.requestDisallowInterceptTouchEvent(true);
			}
		});
		mapLayout.getMapAsync(this);

		detailsButton.setVisibility(TextUtils.isEmpty(entity.getInformation()) ? View.GONE : View.VISIBLE);

		Utils.setTextWithIcon(photoAuthor, R.string.photo_author, entity.getPhoto_author());
		Utils.setTextWithIcon(addressView, R.string.map_marker, entity.getAddress());
		Utils.setTextWithIcon(phoneView, R.string.phone, entity.getPhone());
		Utils.setTextWithIcon(emailView, R.string.envelope, entity.getEmail());
		websiteView.setUrl(entity.getWebsite());

		getLoaderManager().initLoader(PLACE_VIEW_LOADER_ID, createForceBundle(false), new PlaceViewCallbacks())
				.forceLoad();
	}

	@Click
	void detailsButtonClicked() {
		DetailsActivity_.intent(this)
				.text(entity.getInformation())
				.start();
	}

	@Click
	void setRecommendedClicked() {
		progress.setVisibility(View.VISIBLE);
		getLoaderManager().initLoader(PLACE_RECOMMENDED_LOADER_ID, null, new PlaceRecommendedCallbacks())
				.forceLoad();
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
				Bundle bundle = new Bundle();
				bundle.putInt(SELECTED_RATING, ratingDialog.getSelectedRating());
				getLoaderManager().initLoader(PLACE_RATING_LOADER_ID, bundle, new PlaceRatingCallbacks())
						.forceLoad();
			}
		});
		ratingDialog.create().show();
	}

	@UiThread
	void ratingUpdate(String message) {
		progress.setVisibility(View.GONE);
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}

	@OptionsItem({R.id.home, android.R.id.home})
	void homeSelected() {
		if (isInteresting) {
			onBackPressed();
		} else {
			NavUtils.navigateUpFromSameTask(this);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapLayout.init();
		mapLayout.setMarkers(entity);
	}


	class PlaceViewCallbacks implements LoaderManager.LoaderCallbacks<PostResponse> {
		@Override
		public Loader<PostResponse> onCreateLoader(int id, Bundle args) {
			return new AsyncTaskLoader<PostResponse>(getApplicationContext()) {
				@Override
				public PostResponse loadInBackground() {
					return api.setPlaceView(EntityActivity.this.id, getDeviceId());
				}
			};
		}

		@Override
		public void onLoadFinished(Loader<PostResponse> loader, PostResponse data) {
		}

		@Override
		public void onLoaderReset(Loader<PostResponse> loader) {
		}
	}


	class PlaceRecommendedCallbacks implements LoaderManager.LoaderCallbacks<PostResponse> {
		@Override
		public Loader<PostResponse> onCreateLoader(int id, Bundle args) {
			return new AsyncTaskLoader<PostResponse>(getApplicationContext()) {
				@Override
				public PostResponse loadInBackground() {
					return api.setPlaceRecommended(EntityActivity.this.id, getDeviceId());
				}
			};
		}

		@Override
		public void onLoadFinished(Loader<PostResponse> loader, PostResponse data) {
			if (data == null) {
				recommendedUpdate(getString(R.string.loading_error));
			} else {
				recommendedUpdate(data.isResult() ? getString(R.string.recommendation_setted) : data.getError());
			}
		}

		@Override
		public void onLoaderReset(Loader<PostResponse> loader) {
		}
	}


	class PlaceRatingCallbacks implements LoaderManager.LoaderCallbacks<PostResponse> {
		@Override
		public Loader<PostResponse> onCreateLoader(int id, final Bundle args) {
			return new AsyncTaskLoader<PostResponse>(getApplicationContext()) {
				@Override
				public PostResponse loadInBackground() {
					int rating = args.getInt(SELECTED_RATING);
					return api.setPlaceRating(EntityActivity.this.id, getDeviceId(), rating);
				}
			};
		}

		@Override
		public void onLoadFinished(Loader<PostResponse> loader, PostResponse data) {
			if (data == null) {
				ratingUpdate(getString(R.string.loading_error));
			} else {
				ratingUpdate(data.isResult() ? getString(R.string.rating_setted) : data.getError());
			}

		}

		@Override
		public void onLoaderReset(Loader<PostResponse> loader) {
		}
	}
}
