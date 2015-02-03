package ru.saransklife.client;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.EActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by asavinova on 03/02/15.
 */
@EActivity
public class BaseActivity extends ActionBarActivity {

	protected Logger L;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L = LoggerFactory.getLogger(getClass().getSimpleName());
	}

	@Override
	protected void onResume() {
		super.onResume();
		L.info("On resume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		L.info("On pause");
	}

	protected void logExtra(String[] titles, String... extras) {
		StringBuilder builder = new StringBuilder();
		builder.append("Extra: ");
		for (String title : titles) {
			builder.append(title + "=%s, ");
		}
		String log = builder.substring(0, builder.lastIndexOf(","));
		L.info(String.format(log, extras));
	}
}
