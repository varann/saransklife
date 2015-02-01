package ru.saransklife.client.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import ru.saransklife.dao.SectionItem;
import ru.saransklife.client.page.PagerItemFragment_;

/**
 * Created by asavinova on 08/11/14.
 */
public class PagerAdapter extends FragmentPagerAdapter {

	private List<SectionItem> pages;

	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		PagerItemFragment fragment =
				PagerItemFragment_.builder()
						.slug(pages.get(i).getSlug())
						.build();
		return fragment;
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	public void setPages(List<SectionItem> pages) {
		this.pages = pages;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return pages.get(position).getName();
	}
}
