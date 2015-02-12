package ru.saransklife.client;

import android.content.Intent;
import android.view.Window;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import java.util.List;

import ru.saransklife.R;
import ru.saransklife.api.RestApiClient;
import ru.saransklife.api.model.ApiSectionItem;
import ru.saransklife.api.model.MenuResponse;

@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

	@RestService RestApiClient apiClient;
	@Bean Dao dao;

	@AfterViews
	void afterViews() {
		updateMenu();
	}

	@Background
	void updateMenu() {
		try {
			MenuResponse menuResponse = apiClient.getMenu();
			List<ApiSectionItem> apiSectionItems = menuResponse.getResponse();
			dao.setMenuItems(apiSectionItems);
		} catch (RestClientException e) {
			L.error("HTTP error", e);

			//TODO Нужно ли выводить диалог или тоаст с информацией об ошибке?
		} finally {
			startActivity(new Intent(this, MainActivity_.class));
			finish();
		}
	}

}
