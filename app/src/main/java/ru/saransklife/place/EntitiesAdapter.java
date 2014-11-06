package ru.saransklife.place;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import ru.saransklife.R;
import ru.saransklife.dao.PlaceEntityDao;

/**
 * Created by asavinova on 05/11/14.
 */
public class EntitiesAdapter extends RecyclerView.Adapter<EntitiesAdapter.ViewHolder> {

	private static final String imageBaseUrl = "http://images.pointresearch.ru";

	private Cursor cursor;

	public EntitiesAdapter(Cursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.place_entities_recycle_view_item, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int i) {
		cursor.moveToPosition(i);

		String photoPath = cursor.getString(cursor.getColumnIndex(PlaceEntityDao.Properties.Photo_path.columnName));
		String name = cursor.getString(cursor.getColumnIndex(PlaceEntityDao.Properties.Name.columnName));

		ImageLoader.getInstance().displayImage(imageBaseUrl + photoPath, holder.photo);
		holder.name.setText(name);
	}

	@Override
	public int getItemCount() {
		return cursor.getCount();
	}

	public void swapCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView name;
		public ImageView photo;

		public ViewHolder(View view) {
			super(view);
			name = (TextView) view.findViewById(R.id.name);
			photo = (ImageView) view.findViewById(R.id.photo);
		}
	}

}
