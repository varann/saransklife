package ru.saransklife;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by asavinova on 17/10/14.
 */
public class SectionsAdapter extends ArrayAdapter<String> {

	private final TypedArray sectionIcons;
	private final String[] sectionTitles;

	private final LayoutInflater inflater;

	public SectionsAdapter(Context context, int resource) {
		super(context, resource);
		inflater = LayoutInflater.from(context);

		sectionTitles = context.getResources().getStringArray(R.array.sections);
		sectionIcons = context.getResources().obtainTypedArray(R.array.section_icons);

	}

	@Override
	public int getCount() {
		return sectionTitles.length;
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
		holder.icon.setImageResource(sectionIcons.getResourceId(position, -1));
		holder.title.setText(sectionTitles[position]);

		return view;
	}

	static class ViewHolder {
		ImageView icon;
		TextView title;
	}
}
