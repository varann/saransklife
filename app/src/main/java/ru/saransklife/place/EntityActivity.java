package ru.saransklife.place;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.Dao;
import ru.saransklife.R;
import ru.saransklife.Utils;
import ru.saransklife.dao.PlaceEntity;

/**
 * A simple {@link Fragment} subclass.
 */
@EActivity(R.layout.activity_entity)
public class EntityActivity extends Activity {

	@ViewById ImageView photo;

	@ViewById TextView
			name,
			description,
			address,
			phone,
			email,
			website;

	@Bean Dao dao;

	@Extra long id;


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

}
