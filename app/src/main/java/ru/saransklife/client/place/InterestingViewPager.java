package ru.saransklife.client.place;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.viewpagerindicator.CirclePageIndicator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Timer;
import java.util.TimerTask;

import ru.saransklife.R;
import ru.saransklife.client.Dao;

/**
 * Created by asavinova on 05/02/15.
 */
@EViewGroup(R.layout.interesting_viewpager)
public class InterestingViewPager extends FrameLayout {

	@ViewById ViewPager pager;
	@ViewById CirclePageIndicator indicator;

	@Bean Dao dao;

	private Timer timer;
	private TimerTask timerTask;

	private InterestingPagerAdapter interestingPagerAdapter;

	public InterestingViewPager(Context context) {
		super(context);
	}

	public InterestingViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@AfterViews
	void init() {
		interestingPagerAdapter = new InterestingPagerAdapter(dao.getPlaceEntitiesBySlugCursor(Dao.INTERESTING_PLACES_SLUG));
		pager.setAdapter(interestingPagerAdapter);
		indicator.setViewPager(pager);

		pager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				restartTimer();
				view.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});

		restartTimer();
	}

	private void restartTimer() {
		if (timer != null) timer.cancel();
		timer = new Timer();

		if (timerTask != null) timerTask.cancel();
		timerTask = newTimerTask();

		timer.schedule(timerTask, 3000, 2000);
	}

	public void update() {
		interestingPagerAdapter.swapCursor(dao.getPlaceEntitiesBySlugCursor(Dao.INTERESTING_PLACES_SLUG));
		interestingPagerAdapter.notifyDataSetChanged();

		pager.setCurrentItem(0);
		restartTimer();
	}

	@UiThread
	void changeItem() {
		int nextItem = pager.getCurrentItem() + 1;
		if (nextItem == pager.getAdapter().getCount()) {
			pager.setCurrentItem(0);
		} else {
			pager.setCurrentItem(nextItem);
		}
	}

	private TimerTask newTimerTask() {
		return new TimerTask() {
			@Override
			public void run() {
				changeItem();
			}
		};
	}
}
