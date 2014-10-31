package ru.saransklife.place;

import ru.saransklife.R;
import ru.saransklife.menu.SectionItemType;

/**
 * Created by asavinova on 31/10/14.
 */
public enum CategoryType {
	HOTEL(R.drawable.gray_icon_hotel),
	SIGHT(R.drawable.gray_icon_sight),
	EAT(R.drawable.gray_icon_eat),
	MUSEUM(R.drawable.gray_icon_museum),
	THEATRE(R.drawable.gray_icon_theatre),
	PARK(R.drawable.gray_icon_park),
	ENTERTAINMENT(R.drawable.gray_icon_entertainment),
	RELAXPLACE(R.drawable.gray_icon_relaxplace),
	BATH(R.drawable.gray_icon_bath),
	SPORT(R.drawable.gray_icon_sport),
	AUTO(R.drawable.gray_icon_auto),
	SUPERMARKET(R.drawable.gray_icon_supermarket);
	//TODO Добавить тип other

	private String slug;
	private int icon;

	CategoryType(int icon) {
		this.slug = name().toLowerCase();
		this.icon = icon;
	}

	public int getIcon() {
		return icon;
	}

	public static CategoryType findTypeBySlug(String slug) {
		CategoryType[] values = values();
		for (CategoryType type : values) {
			if (type.slug.equalsIgnoreCase(slug)) {
				return type;
			}
		}
		throw new RuntimeException("Slug " + slug + " not found");
	}
}
