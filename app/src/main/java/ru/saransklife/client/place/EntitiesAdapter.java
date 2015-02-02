package ru.saransklife.client.place;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.saransklife.R;
import ru.saransklife.client.Utils;
import ru.saransklife.dao.PlaceEntityDao;

/**
 * Created by asavinova on 05/11/14.
 */
public class EntitiesAdapter extends RecyclerView.Adapter<EntitiesAdapter.ViewHolder> {

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

		holder.setCursor(cursor);
	}

	@Override
	public int getItemCount() {
		return cursor.getCount();
	}

	public void swapCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		public TextView name;
		public ImageView photo;
		private long id;

		public ViewHolder(View view) {
			super(view);
			view.setOnClickListener(this);

			name = (TextView) view.findViewById(R.id.name);
			photo = (ImageView) view.findViewById(R.id.photo);
		}

		@Override
		public void onClick(View v) {
			EntityActivity_.intent(v.getContext()).id(id).start();
		}

		public void setCursor(Cursor cursor) {
			this.id = cursor.getLong(cursor.getColumnIndex(PlaceEntityDao.Properties.Id.columnName));

			String photoPath = cursor.getString(cursor.getColumnIndex(PlaceEntityDao.Properties.Photo_path.columnName));
			String name = cursor.getString(cursor.getColumnIndex(PlaceEntityDao.Properties.Name.columnName));

			if (photoPath != null) {
				Utils.displayImage(this.photo, photoPath);
			}

			this.name.setText(name);
		}
	}

}