package ru.saransklife.client;

import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;

/**
 * Created by asavinova on 06/02/15.
 */
@EActivity(R.layout.activity_details)
public class DetailsActivity extends BaseActivity {

	@ViewById Toolbar toolbar;
	@ViewById WebView webView;

	@Extra String text;
	@Extra long id;

	@AfterViews
	void afterViews() {
		toolbar.setTitle(getString(R.string.details_toolbar_title));
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		webView.loadData(text, "text/html; charset=utf-8", null);
	}

	@OptionsItem({R.id.home, android.R.id.home})
	void upSelected() {
		onBackPressed();
	}
}