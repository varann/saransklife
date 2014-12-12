package ru.saransklife;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

	@ViewById ImageSwitcher imageSwitcher;

	private int[] homeBgArray = {R.drawable.saransk_1, R.drawable.saransk_2, R.drawable.saransk_3};
	private int currentIndex = 0;
	private ScheduledExecutorService scheduleTaskExecutor;
	private ScheduledFuture schedule;


	@AfterViews
	void afterViews() {
		imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
			@Override
			public View makeView() {
				ImageView view = new ImageView(MainActivity.this);
				view.setScaleType(ImageView.ScaleType.CENTER_CROP);
				view.setLayoutParams(new ImageSwitcher.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				return view;
			}
		});

		Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
		in.setDuration(500);
		Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
		out.setDuration(500);
		imageSwitcher.setInAnimation(in);
		imageSwitcher.setOutAnimation(out);

		scheduleTaskExecutor = Executors.newScheduledThreadPool(1);
	}

	@Override
	public void onResume() {
		super.onResume();

		schedule = scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				updateBg();
			}
		}, 0, 5, TimeUnit.SECONDS);
	}

	@Override
	public void onPause() {
		super.onPause();
		schedule.cancel(true);
	}

	@UiThread
	void updateBg() {
		imageSwitcher.setImageResource(homeBgArray[(currentIndex % homeBgArray.length)]);
		currentIndex++;
	}

}
