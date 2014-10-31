package ru.saransklife.place;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.saransklife.R;
import ru.saransklife.dao.PlaceCategoryDao;

/**
 * Created by asavinova on 31/10/14.
 */
public class CategoryAdapter extends CursorAdapter {

	private final LayoutInflater inflater;

	public CategoryAdapter(Context context, Cursor cursor) {
		super(context, cursor, false);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		View view = inflater.inflate(R.layout.place_categories_grid_item, viewGroup, false);

		ViewHolder holder = new ViewHolder();
		holder.icon = (ImageView) view.findViewById(R.id.icon);
		holder.title = (TextView) view.findViewById(R.id.title);
		view.setTag(holder);

		bindView(view, context, cursor);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();

		String slug = cursor.getString(cursor.getColumnIndex(PlaceCategoryDao.Properties.Slug.columnName));
		String title = cursor.getString(cursor.getColumnIndex(PlaceCategoryDao.Properties.Name.columnName));

		holder.icon.setImageResource(CategoryType.findTypeBySlug(slug).getIcon());
		holder.title.setText(title);
	}

	class ViewHolder {
		ImageView icon;
		TextView title;
	}
}
