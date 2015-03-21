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
import ru.saransklife.client.DetailsActivity_;
import ru.saransklife.client.Utils;
import ru.saransklife.client.ui.DescriptionView;
import ru.saransklife.client.ui.DetailsButton;
import ru.saransklife.client.ui.GoogleMapLayout;
import ru.saransklife.client.ui.HazyImageView;
import ru.saransklife.client.ui.NearestSeanceView;
import ru.saransklife.client.ui.TitleView;
import ru.saransklife.client.ui.WhiteTextWithShadowView;
import ru.saransklife.dao.Event;
import ru.saransklife.dao.EventCategory;
import ru.saransklife.dao.EventParams;
import ru.saransklife.dao.PlaceEntity;


@EActivity(R.layout.activity_event_info)
public class EventInfoActivity extends BaseActivity implements OnMapReadyCallback {

	@ViewById Toolbar toolbar;
	@ViewById HazyImageView photo;
	@ViewById WhiteTextWithShadowView price;
	@ViewById DetailsButton detailsButton;
	@ViewById NearestSeanceView seance;
	@ViewById WhiteTextWithShadowView categoryName;
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

			Utils.setTextWithIcon(price, R.string.rub, event.getPrice());

			detailsButton.setVisibility(TextUtils.isEmpty(event.getStory()) && event.getParams() == null ? View.GONE : View.VISIBLE);

			seance.updateSeanceInfo(event);

			EventCategory category = dao.getEventCategoryById(event.getCategory_id());
			Utils.setTextWithIcon(categoryName, R.string.tag, category.getName());

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

	@Click
	void detailsButtonClicked() {
		DetailsActivity_.intent(this)
				.text(getDetailsText())
				.start();
	}

	private String getDetailsText() {
		EventParams params = event.getParams();
		if (params == null) {
			return event.getStory();
		}

		String details = "<table cellspacing=\"10\">";
		details += getTableRow(params.getCountry(), R.string.params_name);
		details += getTableRow(params.getDuration(), R.string.params_duration);
		details += getTableRow(params.getGenre(), R.string.params_genre);
		details += getTableRow(params.getYear(), R.string.params_year);
		details += getTableRow(params.getDirector(), R.string.params_director);
		details += getTableRow(params.getActors(), R.string.params_actors);
		details += getTableRow(params.getCountry(), R.string.params_country);
		details += getTableRow(params.getStart_age(), R.string.params_start_age);
		details += "</table>";

		String info = params.getDescription();

		if (info == null) {
			info = event.getStory();
		}

		if (info == null) {
			info = event.getDescription();
		}

		if (info != null) {
			details += "<p>" + info + "</p>";
		}

		return details;
	}

	private String getTableRow(String parameter, int nameRes) {
		return TextUtils.isEmpty(parameter) ? ""
				: "<tr><td align=\"right\" valign=\"top\">"+ getString(nameRes) + "</td><td>" + parameter + "</td></tr>";
	}

}
