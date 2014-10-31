package ru.saransklife.menu;

import android.support.v4.app.Fragment;

import ru.saransklife.MainFragment_;
import ru.saransklife.PageFragment_;
import ru.saransklife.R;
import ru.saransklife.place.PlaceFragment;
import ru.saransklife.place.PlaceFragment_;

/**
 * Created by asavinova on 30/10/14.
 */
public enum SectionItemType {

	MAIN(R.drawable.white_icon_home, new MainFragment_()),
	PAGE(R.drawable.white_icon_city_about, new PageFragment_()),
	PLACE(R.drawable.white_icon_place, new PlaceFragment_()),
	EVENT(R.drawable.white_icon_event, null),
	REFERENCE(R.drawable.white_icon_reference_book, null),
	ABOUT(R.drawable.white_icon_about, null);

	private String module;
	private final int icon;
	private Fragment fragment;

	SectionItemType(int icon, Fragment fragment) {
		this.module = name().toLowerCase();
		this.icon = icon;
		this.fragment = fragment;
	}

	public String getModule() {
		return module;
	}

	public int getIcon() {
		return icon;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public static SectionItemType findTypeByModule(String module) {
		SectionItemType[] values = values();
		for (SectionItemType sectionItemType : values) {
			if (sectionItemType.module.equalsIgnoreCase(module)) {
				return sectionItemType;
			}
		}
		throw new RuntimeException("Module " + module + " not found");
	}

}
