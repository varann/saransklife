package ru.saransklife.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.saransklife.R;
import ru.saransklife.dao.SectionItem;

/**
 * Created by asavinova on 17/10/14.
 */
public class SectionsAdapter extends ArrayAdapter<SectionItem> {

	private final LayoutInflater inflater;

	public SectionsAdapter(Context context, int resource, List<SectionItem> menu) {
		super(context, resource);
		addAll(menu);

		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;

		if (view == null) {
			view = inflater.inflate(R.layout.list_drawer_item, null);

			holder = new ViewHolder();
			holder.icon = (ImageView) view.findViewById(R.id.icon);
			holder.title = (TextView) view.findViewById(R.id.title);

			view.setTag(holder);
		}

		holder = (ViewHolder) view.getTag();
		SectionItem item = getItem(position);
		holder.icon.setImageResource(SectionItemType.valueOf(item.getModule().toUpperCase()).getIcon());
		holder.title.setText(item.getName());

		return view;
	}

	static class ViewHolder {
		ImageView icon;
		TextView title;
	}
}
