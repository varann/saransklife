package ru.saransklife.client;

import android.content.DialogInterface;
import android.graphics.Color;
import android.net.MailTo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;

/**
 * Created by asavinova on 06/02/15.
 */
@EActivity(R.layout.activity_about)
public class AboutActivity extends BaseActivity {

	@ViewById DrawerLayout drawerLayout;
	@ViewById Toolbar toolbar;
	@ViewById WebView webView;

	@Bean EventBus eventBus;
	@Extra String title;

	@AfterViews
	void afterViews() {
		logExtra(new String[]{"title"}, title);

		toolbar.setTitle(title);
		toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawerLayout.openDrawer(Gravity.START);
			}
		});

		WebViewClient webClient = new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("mailto:")) {
					MailTo mailTo = MailTo.parse(url);
					Utils.mailTo(getApplicationContext(), "mailto:" + mailTo.getTo(), mailTo.getSubject());
					view.reload();
					return true;
				} else {
					view.loadUrl(url);
				}
				return true;
			}
		};
		webView.setWebViewClient(webClient);

		loadPage();
	}

	private void loadPage() {
		DataService_.intent(this)
				.pageAction(Dao.ABOUT_PAGE_SLUG)
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

	public void onEvent(Events.PageLoadedEvent event) {
		updateText(event.getPage().getText());
	}

	public void onEvent(Events.PageLoadErrorEvent event) {
		showErrorDialog(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				loadPage();
			}
		});
	}

	@UiThread
	void updateText(String text) {
		webView.loadData(getFinalText(text), "text/html; charset=utf-8", null);
		webView.setBackgroundColor(Color.TRANSPARENT);
		webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	private String getFinalText(String text) {
		return "<font color=\"white\"><p>"
				+ text
				+ "</p><p style=\"text-align:center\">"
				+ getString(R.string.copyright)
				+ "</p></font>";
	}

}
