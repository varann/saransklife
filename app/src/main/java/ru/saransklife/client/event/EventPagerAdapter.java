package ru.saransklife.client.event;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import ru.saransklife.dao.Event;

/**
 * Created by asavinova on 08/11/14.
 */
public class EventPagerAdapter extends PagerAdapter {
	private static final Logger L = LoggerFactory.getLogger(EventPagerAdapter.class);

	private List<Event> events = new ArrayList<>();

	@Override
	public int getCount() {
		return events.size();
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public int getItemPosition(Object object) {
		/*
		http://stackoverflow.com/questions/7263291/viewpager-pageradapter-not-updating-the-view
		 */
		return POSITION_NONE;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Long id = events.get(position).getId();
		PagerItemView view = PagerItemView_.build(container.getContext());
		view.update(id);
		if (view.getParent() == null) {
			container.addView(view);
		}
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

}
