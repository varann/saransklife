package ru.saransklife.client.place;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ru.saransklife.R;
import ru.saransklife.client.Utils;
import ru.saransklife.client.ui.ItemAddressView;
import ru.saransklife.client.ui.ItemNameView;
import ru.saransklife.client.ui.ItemRecommendedInfoView;
import ru.saransklife.client.ui.RatingView;
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

		public ImageView photo;
		public RatingView rating;
		public ItemNameView name;
		public ItemAddressView address;
		public ItemRecommendedInfoView recommendedInfo;
		private long id;

		public ViewHolder(View view) {
			super(view);
			view.setOnClickListener(this);

			photo = (ImageView) view.findViewById(R.id.photo);
			rating = (RatingView) view.findViewById(R.id.rating_view);
			name = (ItemNameView) view.findViewById(R.id.name);
			address = (ItemAddressView) view.findViewById(R.id.address);
			recommendedInfo = (ItemRecommendedInfoView) view.findViewById(R.id.recommended_info);
		}

		@Override
		public void onClick(View v) {
			EntityActivity_.intent(v.getContext()).id(id).start();
		}

		public void setCursor(Cursor cursor) {
			this.id = cursor.getLong(cursor.getColumnIndex(PlaceEntityDao.Properties.Id.columnName));

			String photoPath = cursor.getString(cursor.getColumnIndex(PlaceEntityDao.Properties.Photo_path.columnName));
			String name = cursor.getString(cursor.getColumnIndex(PlaceEntityDao.Properties.Name.columnName));
			String address = cursor.getString(cursor.getColumnIndex(PlaceEntityDao.Properties.Address.columnName));
			float rating = cursor.getFloat(cursor.getColumnIndex(PlaceEntityDao.Properties.Rating.columnName));
			int viewCount = cursor.getInt(cursor.getColumnIndex(PlaceEntityDao.Properties.View_count.columnName));
			int recommendedCount = cursor.getInt(cursor.getColumnIndex(PlaceEntityDao.Properties.Recommended_count.columnName));

			Utils.displayImage(this.photo, photoPath, null, 200);

			this.rating.setRating(rating);
			this.name.setText(name);
			this.address.setAddress(address);
			this.recommendedInfo.setInfo(viewCount, recommendedCount);
		}
	}

}
