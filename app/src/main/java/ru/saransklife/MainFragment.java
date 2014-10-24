package ru.saransklife;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by asavinova on 23/10/14.
 */
@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

	private static final Logger L = LoggerFactory.getLogger(MainFragment.class.getName());

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
				ImageView view = new ImageView(getActivity());
				view.setScaleType(ImageView.ScaleType.CENTER_CROP);
				view.setLayoutParams(new ImageSwitcher.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				return view;
			}
		});

		Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
		in.setDuration(500);
		Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
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
