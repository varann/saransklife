package ru.saransklife.client.place;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import com.viewpagerindicator.CirclePageIndicator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Timer;
import java.util.TimerTask;

import ru.saransklife.R;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.DataHelper;
import ru.saransklife.client.EventBus;
import ru.saransklife.client.Events;

/**
 * Created by asavinova on 05/02/15.
 */
@EFragment(R.layout.interesting_viewpager)
public class InterestingViewPagerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int LOADER_ID = 1;

	@ViewById ViewPager pager;
	@ViewById CirclePageIndicator indicator;

	@Bean DataHelper dataHelper;
	@Bean EventBus eventBus;

	private Timer timer;
	private TimerTask timerTask;

	private InterestingPagerAdapter interestingPagerAdapter;

	@AfterViews
	void init() {
		interestingPagerAdapter = new InterestingPagerAdapter();
		pager.setAdapter(interestingPagerAdapter);
		indicator.setViewPager(pager);

		pager.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				restartTimer();
				view.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});

		restartTimer();

		getLoaderManager().initLoader(LOADER_ID, ((BaseActivity) getActivity()).createForceBundle(false), this);
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

	public void onEvent(Events.InterestingPlacesLoadedEvent event) {
		getLoaderManager().restartLoader(LOADER_ID, ((BaseActivity) getActivity()).createForceBundle(false), this);
	}

	public void onEvent(Events.InterestingPlacesLoadErrorEvent event) {
		//TODO Show error
	}

	private void restartTimer() {
		if (timer != null) timer.cancel();
		timer = new Timer();

		if (timerTask != null) timerTask.cancel();
		timerTask = newTimerTask();

		timer.schedule(timerTask, 3000, 2000);
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

	@Override
	public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
		return new CursorLoader(getActivity()) {
			@Override
			public Cursor loadInBackground() {
				return dataHelper.getInterestingPlacesCursor(((BaseActivity) getActivity()).isForceBundle(args), getContext());
			}
		};
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		interestingPagerAdapter.swapCursor(cursor);
		interestingPagerAdapter.notifyDataSetChanged();

		pager.setCurrentItem(0);
		restartTimer();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

	public void forceRefresh() {
		getLoaderManager().restartLoader(LOADER_ID, ((BaseActivity) getActivity()).createForceBundle(true), this);
	}
}
