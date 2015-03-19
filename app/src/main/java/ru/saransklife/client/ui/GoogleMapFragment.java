package ru.saransklife.client.ui;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.EFragment;

import ru.saransklife.dao.PlaceEntity;

/**
 * Created by asavinova on 19/03/15.
 */
@EFragment
public class GoogleMapFragment extends MapFragment {

	public void init() {
		GoogleMap map = getMap();

		UiSettings settings = map.getUiSettings();
		settings.setCompassEnabled(true);

		settings.setRotateGesturesEnabled(true);
		settings.setScrollGesturesEnabled(true);
		settings.setTiltGesturesEnabled(true);
		settings.setZoomControlsEnabled(true);
		settings.setZoomGesturesEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}

	public void setMarkers(PlaceEntity place) {
		GoogleMap map = getMap();
		LatLng position = new LatLng(place.getLatitude(), place.getLongitude());
		map.addMarker(new MarkerOptions()
				.position(position)
				.title(place.getName()));

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 13);
		map.moveCamera(update);
	}
}
