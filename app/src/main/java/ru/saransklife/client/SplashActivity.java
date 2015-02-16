package ru.saransklife.client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Window;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.WindowFeature;

import ru.saransklife.R;

@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

	@Bean EventBus eventBus;

	@AfterViews
	void afterViews() {
		loadMenu();
	}

	private void loadMenu() {
		DataService_.intent(this)
				.menuAction()
				.start();
	}

	@Override
	public void onResume() {
		super.onResume();
		eventBus.register(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		eventBus.unregister(this);
	}

	public void onEvent(Events.MenuLoadedEvent event) {
		startActivity(new Intent(this, MainActivity_.class));
		finish();
	}

	public void onEvent(Events.MenuLoadErrorEvent event) {
		showError();
	}

	@UiThread
	void showError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setMessage(R.string.loading_error)
				.setPositiveButton(R.string.dialog_retry, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						loadMenu();
					}
				})
				.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				});

		builder.create().show();
	}

}
