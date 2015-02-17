package ru.saransklife.client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.saransklife.R;


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

	@Override
	public void onBackPressed() {
		if (!closeDrawer()) {
			super.onBackPressed();
		}
	}

	public boolean closeDrawer() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer != null && drawer.isDrawerOpen(Gravity.START)) {
			drawer.closeDrawer(Gravity.START);
			return true;
		}
		return false;
	}

	public String getDeviceId() {
		String deviceId = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.ANDROID_ID);
		return deviceId;
	}

	@UiThread
	public void showErrorDialog(DialogInterface.OnClickListener repeatListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setMessage(R.string.loading_error)
				.setPositiveButton(R.string.dialog_retry, repeatListener)
				.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}
}
