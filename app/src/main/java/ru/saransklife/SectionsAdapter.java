package ru.saransklife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by asavinova on 17/10/14.
 */
public class SectionsAdapter extends ArrayAdapter<String> {

	private String[] sections;

	private final LayoutInflater inflater;

	public SectionsAdapter(Context context, int resource) {
		super(context, resource);
		inflater = LayoutInflater.from(context);

		sections = context.getResources().getStringArray(R.array.sections);
	}

	@Override
	public int getCount() {
		return sections.length;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			view = inflater.inflate(R.layout.list_drawer_item, null);
		}

		((TextView) view.findViewById(R.id.name)).setText(sections[position]);

		return view;
	}
}
