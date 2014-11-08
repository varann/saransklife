package ru.saransklife.event;

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
import ru.saransklife.dao.Event;
import ru.saransklife.dao.PlaceEntity;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
@EFragment(R.layout.fragment_event_info)
public class EventInfoFragment extends Fragment {

	@FragmentArg
	long id;

	@ViewById ImageView photo;

	@ViewById TextView
			name,
			description;

	@Bean Dao dao;

	@AfterViews
	void afterViews() {
		Event event = dao.getEventById(id);

		if (event.getPhoto_path() != null) {
			Utils.displayImage(photo, event.getPhoto_path());
		}

		setText(name, event.getName());
		setText(description, event.getDescription());
	}

	private void setText(TextView view, String text) {
		view.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		view.setText(TextUtils.isEmpty(text) ? "" : text);
	}

	public void setId(long id) {
		this.id = id;
	}
}
