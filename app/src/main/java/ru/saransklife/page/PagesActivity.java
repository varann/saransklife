package ru.saransklife.page;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.Dao;
import ru.saransklife.R;
import ru.saransklife.client.ui.google.sliding_tab_basic.SlidingTabLayout;
import ru.saransklife.drawer.DrawerFragment;

/**
 * Created by asavinova on 13/12/14.
 */
@EActivity(R.layout.activity_pages)
public class PagesActivity extends FragmentActivity {

	@FragmentById DrawerFragment drawer;

	@ViewById DrawerLayout drawerLayout;
	@ViewById Toolbar toolbar;
	@ViewById SlidingTabLayout tabs;
	@ViewById ViewPager pager;

	@Bean Dao dao;

	@AfterViews
	void afterViews() {
		toolbar.setTitle(R.string.page_title);
		toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawerLayout.openDrawer(Gravity.START);
			}
		});

		PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
		adapter.setPages(dao.getPageSectionItems());
		pager.setAdapter(adapter);

		tabs.setSelectedIndicatorColors(getResources().getColor(R.color.accent));
		tabs.setDividerColors(getResources().getColor(android.R.color.transparent));
		tabs.setViewPager(pager);

	}
}
