package ru.saransklife.client;

import android.content.Intent;
import android.graphics.Color;
import android.net.MailTo;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;
import ru.saransklife.dao.Page;

/**
 * Created by asavinova on 06/02/15.
 */
@EActivity(R.layout.activity_about)
public class AboutActivity extends BaseActivity {

	@ViewById DrawerLayout drawerLayout;
	@ViewById Toolbar toolbar;
	@ViewById WebView webView;

	@Bean Dao dao;
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
					Intent intent = new Intent(Intent.ACTION_SENDTO);
					intent.setData(Uri.parse("mailto:" + mailTo.getTo()));
					intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailTo.getTo()});
					intent.putExtra(Intent.EXTRA_SUBJECT, mailTo.getSubject());
					try {
						startActivity(Intent.createChooser(intent, getString(R.string.send_email_title)));
					} catch (android.content.ActivityNotFoundException ex) {
						Toast.makeText(AboutActivity.this, R.string.no_email_applications_installed, Toast.LENGTH_SHORT).show();
					}
					view.reload();
					return true;
				} else {
					view.loadUrl(url);
				}
				return true;
			}
		};
		webView.setWebViewClient(webClient);

		loadText();
	}

	@Background
	void loadText() {
		Page page = dao.getPage(Dao.ABOUT_PAGE_SLUG);
		updateText(page.getText());
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
