package ru.saransklife.client.event;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import ru.saransklife.dao.Event;

/**
 * Created by asavinova on 08/11/14.
 */
public class PagerAdapter extends FragmentPagerAdapter {

	private List<Event> events;

	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		PagerItemFragment fragment =
				PagerItemFragment_.builder().
						id(events.get(i).getId()).
						build();
		return fragment;
	}

	@Override
	public int getCount() {
		return events.size();
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
}
