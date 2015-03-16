package ru.saransklife.client.event;

import ru.saransklife.R;

/**
 * Created by asavinova on 16/03/15.
 */
public enum DateType {
	TODAY(R.string.date_type_today),
	TOMORROW(R.string.date_type_tomorrow),
	WEEKEND(R.string.date_type_weekend),
	WEEK(R.string.date_type_week),
	SOON(R.string.date_type_soon);

	private int text;
	private String type;

	DateType(int text) {
		this.text = text;
		type = name().toLowerCase();
	}

	public int getText() {
		return text;
	}

	public String getType() {
		return type;
	}
}
