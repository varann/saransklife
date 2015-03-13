package ru.saransklife.client.event;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;
import ru.saransklife.client.Dao;
import ru.saransklife.client.Utils;
import ru.saransklife.client.ui.ItemNameView;
import ru.saransklife.dao.Event;

/**
 * Created by asavinova on 08/11/14.
 */
@EViewGroup(R.layout.event_pager_item)
public class PagerItemView extends FrameLayout implements View.OnClickListener {

	@ViewById ImageView photo;
	@ViewById ItemNameView name;

	@Bean Dao dao;
	private long id;

	public PagerItemView(Context context) {
		super(context);
		setOnClickListener(this);
	}

	public void update(long id) {
		this.id = id;
		Event event = dao.getEventById(id);

		Utils.displayImage(photo, event.getPhoto_path(), null, 200);
		name.setText(event.getName());
	}

	@Override
	public void onClick(View v) {
		EventInfoActivity_.intent(getContext()).id(id).start();
	}
}
