package ru.saransklife.client.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.saransklife.R;

/**
 * Created by asavinova on 15/03/15.
 */
public enum CategoryType {
	ACTIVE_REST(R.drawable.icon64_gray_active_rest),
	ART(R.drawable.icon64_gray_art),
	CHILDREN(R.drawable.icon64_gray_children),
	CINEMA(R.drawable.icon64_gray_cinema),
	CITY(R.drawable.icon64_gray_city),
	MUSEUM(R.drawable.icon64_gray_museum),
	PARTY(R.drawable.icon64_gray_party),
	THEATRE(R.drawable.icon64_gray_theatre);

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
		L.warn("Неизвестная категория афиши, slug = " + slug);
		return null;
	}
}
