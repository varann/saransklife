package ru.saransklife.client;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;

import ru.saransklife.R;
import ru.saransklife.api.ApiCLient;
import ru.saransklife.api.model.LoginResponse;

@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

	private static final int LOGIN_LOADER_ID = 0;

	@ViewById ImageView back;

	@Pref Preferences_ preferences;
	@Bean EventBus eventBus;
	@Bean ApiCLient api;

	@AfterViews
	void afterViews() {
		Picasso.with(this).load(R.drawable.saransk_1).into(back);

		String token = preferences.token().get();
		if (token.isEmpty()) {
			getLoaderManager().initLoader(LOGIN_LOADER_ID, null, new LoginCallbacks());
		} else {
			loadMenu();
		}
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
		showError(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				loadMenu();
			}
		});
	}

	@UiThread
	void showError(DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setMessage(R.string.loading_error)
				.setPositiveButton(R.string.dialog_retry, listener)
				.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				});

		builder.create().show();
	}


	class LoginCallbacks implements LoaderManager.LoaderCallbacks<LoginResponse> {

		@Override
		public Loader<LoginResponse> onCreateLoader(int id, final Bundle args) {
			return new AsyncTaskLoader<LoginResponse>(getApplicationContext()) {
				@Override
				protected void onStartLoading() {
					super.onStartLoading();
					forceLoad();
				}

				@Override
				public LoginResponse loadInBackground() {
					return api.getLogin();
				}
			};
		}

		@Override
		public void onLoadFinished(Loader<LoginResponse> loader, LoginResponse data) {
			if (data == null) {
				showError(new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						getLoaderManager().restartLoader(LOGIN_LOADER_ID, null, new LoginCallbacks());
					}
				});
			} else {
				preferences.token().put(data.getResponse().getToken());
				loadMenu();
			}
		}

		@Override
		public void onLoaderReset(Loader<LoginResponse> loader) {
		}
	}
}
