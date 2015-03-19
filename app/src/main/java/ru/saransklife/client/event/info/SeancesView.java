package ru.saransklife.client.event.info;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ru.saransklife.R;
import ru.saransklife.client.ui.SubTitleView;
import ru.saransklife.dao.Event;
import ru.saransklife.dao.PlaceEntity;
import ru.saransklife.dao.Seance;

/**
 * Created by asavinova on 18/03/15.
 */
@EViewGroup(R.layout.seances_view)
public class SeancesView extends LinearLayout {

	private String pattern = "yyyy-MM-dd";
	private SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

	@ViewById SubTitleView seancesHeader;
	@ViewById LinearLayout datesPanel;
	@ViewById LinearLayout seancesPanel;

	private TreeMap<Date, HashMap<Long, List<Seance>>> calendar;
	private Map<Long, PlaceEntity> placesMap;
	private Date current;

	public SeancesView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void update(Event event) {
		List<Seance> seances = event.getSeances();

		if (seances.size() > 1) {
			setVisibility(View.VISIBLE);
			seancesHeader.setTitle(R.string.calendar, getResources().getString(R.string.seances_header));

			List<PlaceEntity> places = event.getPlaces();
			placesMap = new HashMap<>();
			for (PlaceEntity place : places) {
				placesMap.put(place.getId(), place);
			}

			createCalendar(seances, places);

			Set<Date> dates = calendar.keySet();
			fillDatesPanel(dates);
		} else {
			setVisibility(View.GONE);
		}
	}

	private void fillDatesPanel(Set<Date> dates) {
		datesPanel.removeAllViews();
		for (Date date : dates) {
			DateView dateView = DateView_.build(getContext());
			dateView.setDate(date);

			dateView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < datesPanel.getChildCount(); i++) {
						DateView view = (DateView) datesPanel.getChildAt(i);
						view.setSelectedView(false);
					}
					DateView currentView = (DateView) v;
					currentView.setSelectedView(true);
					current = currentView.getDate();
					updateSeances();
				}
			});

			datesPanel.addView(dateView);
		}
		datesPanel.getChildAt(0).callOnClick();
	}

	private void updateSeances() {
		seancesPanel.removeAllViews();

		boolean showPlaceInfo = placesMap.size() != 1;
		HashMap<Long, List<Seance>> places = calendar.get(current);
		for (Long id : places.keySet()) {
			PlaceSeancesView view = PlaceSeancesView_.build(getContext());
			view.setData(placesMap.get(id), places.get(id), showPlaceInfo);
			seancesPanel.addView(view);
		}
	}

	private void createCalendar(List<Seance> seances, List<PlaceEntity> places) {
		calendar = new TreeMap<>();

		TreeMap<Date, List<Seance>> dateMap = new TreeMap<>();
		for (Seance seance : seances) {

			boolean isNewDate = true;
			Date seanceDate = seance.getDatetime();
			for (Date date : dateMap.keySet()) {

				if (dateFormat.format(seanceDate).equals(dateFormat.format(date))) {
					dateMap.get(date).add(seance);
					isNewDate = false;
					break;
				}
			}

			if (isNewDate) {
				ArrayList<Seance> list = new ArrayList<>();
				list.add(seance);
				dateMap.put(seanceDate, list);
			}
		}

		Set<Date> dates = dateMap.keySet();
		for (Date date : dates) {
			HashMap<Long, List<Seance>> placesMap = new HashMap<>();

			List<Seance> seancesForDate = dateMap.get(date);
			if (places.size() == 1) {
				placesMap.put(places.get(0).getId(), seancesForDate);
			} else {
				for (PlaceEntity place : places) {

					List<Seance> list = new ArrayList<>();
					for (Seance seance : seancesForDate) {
						if (seance.getPlaceId() == place.getId()) {
							list.add(seance);
						}
					}
					if (!list.isEmpty()) {
						placesMap.put(place.getId(), list);
					}
				}
			}

			calendar.put(date, placesMap);
		}

		current = dates.iterator().next();
	}

}
