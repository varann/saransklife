package ru.saransklife.menu;

import ru.saransklife.R;

/**
 * Created by asavinova on 30/10/14.
 */
public enum SectionItemType {

	MAIN(R.drawable.white_icon_home),
	PAGE(R.drawable.white_icon_city_about),
	PLACE(R.drawable.white_icon_place),
	EVENT(R.drawable.white_icon_event),
	REFERENCE(R.drawable.white_icon_reference_book),
	ABOUT(R.drawable.white_icon_about);

	private String module;
	private final int icon;

	SectionItemType(int icon) {
		this.module = name();
		this.icon = icon;
	}

	public int getIcon() {
		return icon;
	}

	public static int findIconByModule(String module) {
		SectionItemType[] values = values();
		for (SectionItemType sectionItemType : values) {
			if (sectionItemType.module.equalsIgnoreCase(module)) {
				return sectionItemType.icon;
			}
		}
		throw new RuntimeException("Module " + module + " not found");
	}

}
