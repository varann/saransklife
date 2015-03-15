package ru.saransklife.client.place.entities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.saransklife.R;
import ru.saransklife.dao.PlaceCategory;

/**
 * Created by asavinova on 13/03/15.
 */
public class SubCategoryAdapter extends BaseAdapter {

	private List<PlaceCategory> categories = new ArrayList<>();

	public void setCategories(List<PlaceCategory> categories) {
		this.categories = categories;
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Object getItem(int position) {
		return categories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return categories.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.spinner_dropdown_item, null);
		String name = ((PlaceCategory)getItem(position)).getName();
		TextView textView = (TextView) view.findViewById(R.id.title);
		textView.setText(name);

		return view;
	}
}
