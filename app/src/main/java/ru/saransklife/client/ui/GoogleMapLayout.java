package ru.saransklife.client.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.FragmentById;

import java.util.List;

import ru.saransklife.R;
import ru.saransklife.dao.PlaceEntity;

/**
 * Created by asavinova on 19/03/15.
 * http://www.londatiga.net/it/programming/android/how-to-make-android-map-scrollable-inside-a-scrollview-layout/
 */
@EViewGroup(R.layout.google_map_layout)
public class GoogleMapLayout extends FrameLayout {

	public interface OnTouchListener {
		public abstract void onTouch();
	}

	@FragmentById MapFragment mapFragment;

	private OnTouchListener listener;

	public GoogleMapLayout(Context context) {
		super(context);
	}

	public GoogleMapLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GoogleMapLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void getMapAsync(OnMapReadyCallback callback) {
		mapFragment.getMapAsync(callback);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (listener != null) listener.onTouch();
				break;
			case MotionEvent.ACTION_UP:
				if (listener != null) listener.onTouch();
				break;
		}
		return super.dispatchTouchEvent(event);
	}

	public void setListener(OnTouchListener listener) {
		this.listener = listener;
	}


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

	private GoogleMap getMap() {
		return mapFragment.getMap();
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

	public void setMarkers(List<PlaceEntity> places) {
		GoogleMap map = getMap();

		for (PlaceEntity place : places) {

			map.addMarker(new MarkerOptions()
					.position(new LatLng(place.getLatitude(), place.getLongitude()))
					.title(place.getName()));
		}

		PlaceEntity place = places.get(0);
		LatLng position = new LatLng(place.getLatitude(), place.getLongitude());
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 13);
		map.moveCamera(update);
	}
}
