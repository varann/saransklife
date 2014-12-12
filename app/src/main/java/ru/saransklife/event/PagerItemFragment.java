package ru.saransklife.event;

import android.support.v4.app.Fragment;
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
import ru.saransklife.EventBus;
import ru.saransklife.R;
import ru.saransklife.Utils;
import ru.saransklife.dao.Event;

/**
 * Created by asavinova on 08/11/14.
 */
@EFragment(R.layout.event_pager_item)
public class PagerItemFragment extends Fragment implements View.OnClickListener {

	@FragmentArg long id;

	@ViewById ImageView photo;
	@ViewById TextView name;

	@Bean Dao dao;

	@AfterViews
	void afterViews() {
		getView().setOnClickListener(this);

		Event event = dao.getEventById(id);

		if (event.getPhoto_path() != null) {
			Utils.displayImage(photo, event.getPhoto_path());
		}

		name.setText(event.getName());
	}

	@Override
	public void onClick(View v) {
		EventInfoActivity_.intent(getActivity()).id(id).start();
	}
}
