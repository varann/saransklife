package ru.saransklife.client;

import android.graphics.Point;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import ru.saransklife.R;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

	@ViewById DrawerLayout drawerLayout;
	@ViewById Toolbar toolbar;
	@ViewById ImageView image1;
	@ViewById ImageView image2;
	@ViewById ImageView image3;

	private int currentIndex = 0;
	private ScheduledExecutorService scheduleTaskExecutor;
	private ScheduledFuture schedule;

	private List<ImageView> images = new ArrayList<>();
	private Animation inAnimation;
	private Animation outAnimation;

	public MainActivity() {
	}

	@AfterViews
	void afterViews() {
		toolbar.setTitle(R.string.main_title);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy г.", new Locale("ru", "RU"));
		toolbar.setSubtitle(simpleDateFormat.format(new Date()));
		toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawerLayout.openDrawer(Gravity.START);
			}
		});

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		Picasso.with(this).load(R.drawable.saransk_1).resize(size.x, size.y).centerCrop().into(image1);
		Picasso.with(this).load(R.drawable.saransk_2).resize(size.x, size.y).centerCrop().into(image2);
		Picasso.with(this).load(R.drawable.saransk_3).resize(size.x, size.y).centerCrop().into(image3);
		images.add(image1);
		images.add(image2);
		images.add(image3);

		inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
		inAnimation.setDuration(500);
		outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
		outAnimation.setDuration(500);

		scheduleTaskExecutor = Executors.newScheduledThreadPool(1);
	}

	@Override
	public void onResume() {
		super.onResume();

		schedule = scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				updateImage();
			}
		}, 0, 5, TimeUnit.SECONDS);
	}

	@UiThread
	void updateImage() {
		final ImageView oldImage = images.get(currentIndex);

		outAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				oldImage.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
		oldImage.startAnimation(outAnimation);

		currentIndex++;
		currentIndex = currentIndex % images.size();

		final ImageView newImage = images.get(currentIndex);

		inAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				newImage.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
		newImage.startAnimation(inAnimation);
	}

	@Override
	public void onPause() {
		super.onPause();
		schedule.cancel(true);
	}

}
