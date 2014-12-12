package ru.saransklife;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.List;

import ru.saransklife.api.RestApiClient;
import ru.saransklife.dao.Page;
import ru.saransklife.dao.SectionItem;

/**
 * Created by asavinova on 13/12/14.
 */
@EActivity(R.layout.activity_pages)
public class PagesActivity extends FragmentActivity {

	@ViewById WebView webView;

	@Bean Dao dao;
	@RestService RestApiClient apiClient;

	private List<SectionItem> pageSections;

	@AfterViews
	void afterViews() {
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setDefaultTextEncodingName("UTF-8");

		pageSections = dao.getPageSectionItems();
		List<String> sectionTitles = new ArrayList<String>();
		for (SectionItem item : pageSections) {
			sectionTitles.add(item.getName());
		}

		SpinnerAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sectionTitles);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(adapter, new ActionBar.OnNavigationListener() {
			@Override
			public boolean onNavigationItemSelected(int position, long itemId) {
				getPage(pageSections.get(position).getSlug());
				return true;
			}
		});
	}

	@Background
	void getPage(String slug) {
		updatePage(dao.getPage(slug));
	}

	@UiThread
	void updatePage(Page page) {
		webView.loadData(page.getText(), "text/html; charset=utf-8", null);
	}
}
