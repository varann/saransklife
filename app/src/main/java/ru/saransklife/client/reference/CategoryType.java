package ru.saransklife.client.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.saransklife.R;

/**
 * Created by asavinova on 31/10/14.
 */
public enum CategoryType {
	EMERGENCY(R.drawable.ref_gray_icon_emergency),
	EMERGENCY_HELP(R.drawable.ref_gray_icon_emergency_help),
	INFO(R.drawable.ref_gray_icon_info),
	CITY(R.drawable.ref_gray_icon_city),
	TAXI(R.drawable.ref_gray_icon_taxi),
	MED_CENTER_PRIVATE(R.drawable.ref_gray_icon_med_center_private),
	MED_CITY(R.drawable.ref_gray_icon_med_city),
	MINISTRY(R.drawable.ref_gray_icon_ministry),
	AUTO_KOMMISAR(R.drawable.ref_gray_icon_auto_kommisar),
	WRECKER(R.drawable.ref_gray_icon_wrecker),
	UNKNOWN(0);

	private static Logger L = LoggerFactory.getLogger(CategoryType.class);

	private String slug;
	private int icon;

	CategoryType(int icon) {
		this.slug = name().toLowerCase().replace("_", "-");
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
		L.warn("Неизвестная категория справочника, slug = " + slug);
		return UNKNOWN;
	}
}
