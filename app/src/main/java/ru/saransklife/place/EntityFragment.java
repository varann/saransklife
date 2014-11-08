package ru.saransklife.place;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.Dao;
import ru.saransklife.R;
import ru.saransklife.Utils;
import ru.saransklife.dao.PlaceEntity;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_entity)
public class EntityFragment extends Fragment {

	@FragmentArg
	long id;

	@ViewById ImageView photo;

	@ViewById TextView
			name,
			description,
			address,
			phone,
			email,
			website;

	@Bean Dao dao;

	@AfterViews
	void afterViews() {
		PlaceEntity entity = dao.getPlaceEntity(id);

		if (entity.getPhoto_path() != null) {
			Utils.displayImage(photo, entity.getPhoto_path());
		}

		setText(name, entity.getName());
		setText(description, entity.getDescription());
		setText(address, entity.getAddress());
		setText(phone, entity.getPhone());
		setText(email, entity.getEmail());
		setText(website, entity.getWebsite());
	}

	private void setText(TextView view, String text) {
		view.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		view.setText(TextUtils.isEmpty(text) ? "" : text);
	}

	public void setId(long id) {
		this.id = id;
	}
}
