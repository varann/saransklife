package ru.saransklife.client.page;

import android.support.v4.app.Fragment;
import android.webkit.WebView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.client.Dao;
import ru.saransklife.R;
import ru.saransklife.dao.Page;

/**
 * Created by asavinova on 08/11/14.
 */
@EFragment(R.layout.page_pager_item)
public class PagerItemFragment extends Fragment {

	@FragmentArg String slug;

	@ViewById WebView webView;

	@Bean Dao dao;

	@AfterViews
	void afterViews() {
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setDefaultTextEncodingName("UTF-8");

		loadPage();
	}

	@Background
	void loadPage() {
		Page page = dao.getPage(slug);
		updatePage(page);
	}

	@UiThread
	void updatePage(Page page) {
		webView.loadData(page.getText(), "text/html; charset=utf-8", null);
	}
}
