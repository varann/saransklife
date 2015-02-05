package ru.saransklife.client.ui;

import android.content.Context;
import android.util.AttributeSet;

import ru.saransklife.R;

/**
 * Created by asavinova on 05/02/15.
 */
public class ItemAddressView extends AwesomeIconTextView {

	public ItemAddressView(Context context) {
		super(context, null);
	}

	public ItemAddressView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setShadowLayer(1, 1, 1, R.color.shadow);
	}

	public void setAddress(String address) {
		String marker = getResources().getString(R.string.map_marker);
		setText(marker + " " + address);
	}
}
