package ru.saransklife.client.event;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import ru.saransklife.R;
import ru.saransklife.client.Dao;
import ru.saransklife.dao.Event;
import ru.saransklife.dao.EventCategoryDao;

/**
 * Created by asavinova on 05/11/14.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

	private Cursor cursor;
	private Dao dao;

	public EventsAdapter(Cursor cursor, Dao dao) {
		this.cursor = cursor;
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
		cursor.moveToPosition(i);
		holder.setParams(cursor, dao);
	}

	@Override
	public int getItemCount() {
		if (cursor == null) return 0;
		return cursor.getCount();
	}

	public void swapCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView icon;
		public TextView name;
		public ViewPager pager;
		public CirclePageIndicator indicator;
		public EventPagerAdapter adapter;

		public ViewHolder(View view) {
			super(view);
			icon = (ImageView) view.findViewById(R.id.icon);
			name = (TextView) view.findViewById(R.id.name);
			pager = (ViewPager) view.findViewById(R.id.pager);
			indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);

			adapter = new EventPagerAdapter();
			pager.setAdapter(adapter);
			indicator.setViewPager(pager);
		}

		public void setParams(Cursor cursor, Dao dao) {
			String name = cursor.getString(cursor.getColumnIndex(EventCategoryDao.Properties.Name.columnName));
			this.name.setText(name);

			String slug = cursor.getString(cursor.getColumnIndex(EventCategoryDao.Properties.Slug.columnName));
			CategoryType type = CategoryType.findTypeBySlug(slug);
			if (type == null) {
				this.icon.setVisibility(View.GONE);
			} else {
				this.icon.setImageResource(type.getIcon());
				this.icon.setVisibility(View.VISIBLE);
			}

			long id = cursor.getLong(cursor.getColumnIndex(EventCategoryDao.Properties.Id.columnName));
			List<Event> events = dao.getEventsByCategory(id);
			adapter.setEvents(events);
			adapter.notifyDataSetChanged();
		}

	}

}
