package ru.saransklife.client.page;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;
import ru.saransklife.client.DataService_;
import ru.saransklife.client.EventBus;
import ru.saransklife.client.Events;

/**
 * Created by asavinova on 08/11/14.
 */
@EFragment(R.layout.page_pager_item)
public class PagerItemFragment extends Fragment {

	@FragmentArg String slug;

	@ViewById WebView webView;
	@ViewById ProgressBar progress;

	@Bean EventBus eventBus;

	@AfterViews
	void afterViews() {
		webView.getSettings().setDefaultTextEncodingName("UTF-8");

		WebViewClient webClient = new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("reload")) {
					loadPage();
				} else {
					view.loadUrl(url);
				}
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				progress.setVisibility(View.GONE);
			}
		};
		webView.setWebViewClient(webClient);

		loadPage();
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

	private void loadPage() {
		progress.setVisibility(View.VISIBLE);
		DataService_.intent(getActivity())
				.pageAction(slug)
				.start();
	}

	public void onEvent(Events.PageLoadedEvent event) {
		if (slug.equals(event.getPage().getSlug())) {
			updateText(event.getPage().getText());
		}
	}

	public void onEvent(Events.PageLoadErrorEvent event) {
		if (slug.equals(event.getSlug())) {
			updateText(String.format("<div style=\"text-align: center\"><p>%s</p><p><a href=\"reload\">%s</a></p></div>",
					getString(R.string.loading_error),
					getString(R.string.dialog_retry)));
		}
	}

	@UiThread
	void updateText(String text) {
		text = text.replaceAll("<img src", "<img width=\"100%\" src");
		webView.loadData(text, "text/html; charset=utf-8", null);
	}
}
