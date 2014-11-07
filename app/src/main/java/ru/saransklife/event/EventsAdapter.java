package ru.saransklife.event;

import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.saransklife.Dao;
import ru.saransklife.R;
import ru.saransklife.dao.Event;
import ru.saransklife.dao.EventCategory;
import ru.saransklife.dao.PlaceEntityDao;

/**
 * Created by asavinova on 05/11/14.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

	private FragmentManager childFragmentManager;
	private Dao dao;
	private List<EventCategory> categories;

	public EventsAdapter(List<EventCategory> categories, FragmentManager childFragmentManager, Dao dao) {
		this.categories = categories;
		this.childFragmentManager = childFragmentManager;
		this.dao = dao;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.event_category_recycle_view_item, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int i) {
		holder.setParams(childFragmentManager, dao, categories.get(i));
	}

	@Override
	public int getItemCount() {
		return categories.size();
	}

	public void setCategories(List<EventCategory> categories) {
		this.categories = categories;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView name;
		public ViewPager pager;

		public ViewHolder(View view) {
			super(view);
			name = (TextView) view.findViewById(R.id.name);
			pager = (ViewPager) view.findViewById(R.id.pager);
		}

		public void setParams(FragmentManager fragmentManager, Dao dao, EventCategory category) {
			this.name.setText(category.getName());

			PagerAdapter adapter = new PagerAdapter(fragmentManager);
			List<Event> events = dao.getEventsByCategory(category.getId());
			adapter.setEvents(events);
			this.pager.setAdapter(adapter);
		}

	}

}
