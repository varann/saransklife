package ru.saransklife;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.androidannotations.annotations.EApplication;

/**
 * Created by asavinova on 06/11/14.
 */
@EApplication
public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		initImageLoader();
	}

	private void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
				.defaultDisplayImageOptions(new DisplayImageOptions.Builder()
						.build())
				.build();

		ImageLoader.getInstance().init(config);
	}
}
