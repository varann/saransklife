package ru.saransklife.client.place;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ru.saransklife.R;
import ru.saransklife.client.Utils;
import ru.saransklife.client.ui.ItemAddressView;
import ru.saransklife.client.ui.ItemNameView;
import ru.saransklife.dao.PlaceEntityDao;

/**
 * Created by asavinova on 04/02/15.
 */
public class InterestingPagerAdapter extends PagerAdapter {

	private Cursor cursor;

	public InterestingPagerAdapter(Cursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public int getCount() {
		return cursor.getCount();
	}

	public void swapCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final Context context = container.getContext();
		View view = LayoutInflater.from(context).inflate(R.layout.interesting_pager_item, null);
		ImageView photo = (ImageView) view.findViewById(R.id.photo);
		ItemNameView nameView = (ItemNameView) view.findViewById(R.id.name);
		ItemAddressView addressView = (ItemAddressView) view.findViewById(R.id.address);

		cursor.moveToPosition(position);
		final long id = cursor.getLong(cursor.getColumnIndex(PlaceEntityDao.Properties.Id.columnName));
		String photoPath = cursor.getString(cursor.getColumnIndex(PlaceEntityDao.Properties.Photo_path.columnName));
		String name = cursor.getString(cursor.getColumnIndex(PlaceEntityDao.Properties.Name.columnName));
		String address = cursor.getString(cursor.getColumnIndex(PlaceEntityDao.Properties.Address.columnName));

		if (photoPath != null) {
			Utils.displayImage(photo, photoPath);
		}
		nameView.setText(name);
		addressView.setAddress(address);

		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EntityActivity_.intent(context)
						.id(id)
						.isInteresting(true)
						.start();
			}
		});

		if (view.getParent() == null) {
			container.addView(view);
		}
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}

