package ru.saransklife.client.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ru.saransklife.R;

/**
 * Created by asavinova on 13/03/15.
 */
public class DateTypeAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		return DateType.values().length;
	}

	@Override
	public Object getItem(int position) {
		return DateType.values()[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.spinner_dropdown_item, null);
		int text = ((DateType)getItem(position)).getText();
		TextView textView = (TextView) view.findViewById(R.id.title);
		textView.setText(text);

		return view;
	}
}
