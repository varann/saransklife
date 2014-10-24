package ru.saransklife;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.support.v4.app.FragmentManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

	@Pref Preferences_ preferences;
	@InstanceState int currentSelectedPosition;

	@ViewById DrawerLayout drawerLayout;

	@ViewById ListView listDrawer;
	private boolean userLearnedDrawer;
	private ActionBarDrawerToggle drawerToggle;

	@AfterViews
	void afterViews() {

		userLearnedDrawer = preferences.navigationDrawerLearned().get();

		listDrawer.setAdapter(new SectionsAdapter(this, R.layout.list_drawer_item));
		listDrawer.setItemChecked(currentSelectedPosition, true);

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
		fragmentManager.beginTransaction()
				.replace(R.id.container, new MainFragment_())
				.commit();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		drawerToggle.onConfigurationChanged(newConfig);
	}


}
