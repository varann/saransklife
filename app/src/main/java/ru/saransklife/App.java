package ru.saransklife;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheOnDisk(true)
				.cacheInMemory(true)
				.resetViewBeforeLoading(true)
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
				.defaultDisplayImageOptions(options)
				.discCacheSize(1024 * 1024 * 512)
				.build();

		ImageLoader.getInstance().init(config);
	}
}
