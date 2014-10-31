package ru.saransklife;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import ru.saransklife.dao.SectionItem;
import ru.saransklife.menu.SectionItemType;
import ru.saransklife.menu.SectionsAdapter;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

	@Bean Dao dao;
	@Pref Preferences_ preferences;
	@InstanceState int currentSelectedPosition;

	@ViewById DrawerLayout drawerLayout;

	@ViewById ListView listDrawer;
	private boolean userLearnedDrawer;
	private ActionBarDrawerToggle drawerToggle;
	private List<SectionItem> sectionItems;

	@AfterViews
	void afterViews() {

		getSections();

		userLearnedDrawer = preferences.navigationDrawerLearned().get();

		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(
				this, drawerLayout, R.drawable.ic_drawer, 0, 0) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);

				if (!userLearnedDrawer) {
					userLearnedDrawer = true;
					preferences.edit().navigationDrawerLearned().put(true).apply();
				}
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);

		if (!userLearnedDrawer) {
			this.drawerLayout.openDrawer(listDrawer);
		}

	}

	@Background
	void getSections() {
		sectionItems = dao.getRootMenuItems();
		updateSections();
	}

	@UiThread
	void updateSections() {
		listDrawer.setAdapter(new SectionsAdapter(this, R.layout.list_drawer_item, sectionItems));
		listDrawer.setItemChecked(currentSelectedPosition, true);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@ItemClick
	void listDrawerItemClicked(int position) {
		currentSelectedPosition = position;
		if (listDrawer != null) {
			listDrawer.setItemChecked(position, true);
		}
		if (drawerLayout != null) {
			drawerLayout.closeDrawer(listDrawer);
		}

		FragmentManager fragmentManager = getSupportFragmentManager();

		String module = sectionItems.get(position).getModule();
		Fragment fragment = SectionItemType.findTypeByModule(module).getFragment();

		fragmentManager.beginTransaction()
				.replace(R.id.container, fragment)
				.commit();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		drawerToggle.onConfigurationChanged(newConfig);
	}


}
