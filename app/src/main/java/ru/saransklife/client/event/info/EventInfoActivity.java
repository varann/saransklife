package ru.saransklife.client.event.info;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import ru.saransklife.R;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;
import ru.saransklife.client.DetailsActivity;
import ru.saransklife.client.DetailsActivity_;
import ru.saransklife.client.Utils;
import ru.saransklife.client.ui.DescriptionView;
import ru.saransklife.client.ui.DetailsButton;
import ru.saransklife.client.ui.GoogleMapLayout;
import ru.saransklife.client.ui.HazyImageView;
import ru.saransklife.client.ui.NearestSeanceView;
import ru.saransklife.client.ui.TitleView;
import ru.saransklife.dao.Event;
import ru.saransklife.dao.EventCategory;
import ru.saransklife.dao.PlaceEntity;


@EActivity(R.layout.activity_event_info)
public class EventInfoActivity extends BaseActivity implements OnMapReadyCallback {

	@ViewById Toolbar toolbar;
	@ViewById HazyImageView photo;
	@ViewById DetailsButton detailsButton;
	@ViewById NearestSeanceView seance;
	@ViewById TextView categoryName;
	@ViewById TitleView titleView;
	@ViewById DescriptionView descriptionView;
	@ViewById GoogleMapLayout mapLayout;
	@ViewById TextView place;
	@ViewById TextView address;
	@ViewById TextView message;
	@ViewById LinearLayout eventLayout;
	@ViewById SeancesView seancesView;
	@ViewById ScrollView scroll;

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

			Utils.displayImage(photo, event.getPhoto_path(), null, 200);

			detailsButton.setVisibility(TextUtils.isEmpty(event.getStory()) ? View.GONE : View.VISIBLE);

			seance.updateSeanceInfo(event);

			EventCategory category = dao.getEventCategoryById(event.getCategory_id());
			setText(categoryName, category.getName());
			titleView.setTitle(event.getName());
			descriptionView.setText(event.getDescription());

			seancesView.update(event);

			mapLayout.setListener(new GoogleMapLayout.OnTouchListener() {
				@Override
				public void onTouch() {
					scroll.requestDisallowInterceptTouchEvent(true);
				}
			});
			mapLayout.getMapAsync(this);
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

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapLayout.init();

		List<PlaceEntity> places = event.getPlaces();
		mapLayout.setMarkers(places);
		if (places.size() > 1) {
			place.setText(R.string.some_places);
			address.setVisibility(View.GONE);
		} else {
			PlaceEntity placeEntity = places.get(0);
			place.setText(placeEntity.getName());
			address.setText(placeEntity.getAddress());
			address.setVisibility(View.VISIBLE);
		}
	}

}
