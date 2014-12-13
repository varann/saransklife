package ru.saransklife.drawer;

import ru.saransklife.MainActivity_;
import ru.saransklife.page.PagesActivity_;
import ru.saransklife.R;
import ru.saransklife.event.EventsActivity_;
import ru.saransklife.place.PlaceCategoriesActivity_;

/**
 * Created by asavinova on 30/10/14.
 */
public enum SectionItemType {

	MAIN(R.drawable.white_icon_home, MainActivity_.class),
	PAGE(R.drawable.white_icon_city_about, PagesActivity_.class),
	PLACE(R.drawable.white_icon_place, PlaceCategoriesActivity_.class),
	EVENT(R.drawable.white_icon_event, EventsActivity_.class),
	REFERENCE(R.drawable.white_icon_reference_book, null),
	ABOUT(R.drawable.white_icon_about, null);

	private final int icon;
	private Class clazz;

	SectionItemType(int icon, Class clazz) {
		this.icon = icon;
		this.clazz = clazz;
	}

	public int getIcon() {
		return icon;
	}

	public Class getClazz() {
		return clazz;
	}

}
