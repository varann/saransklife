package ru.saransklife.drawer;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import ru.saransklife.Dao;
import ru.saransklife.R;
import ru.saransklife.dao.SectionItem;

/**
 * Created by asavinova on 13/12/14.
 */
@EFragment(R.layout.fragment_drawer)
public class DrawerFragment extends Fragment {

	@ViewById ListView listDrawer;

	@Bean Dao dao;

	private List<SectionItem> sectionItems;


	@AfterViews
	void afterViews() {
		sectionItems = dao.getRootMenuItems();
		listDrawer.setAdapter(new SectionsAdapter(getActivity(), R.layout.list_drawer_item, sectionItems));
	}

	@ItemClick
	void listDrawerItemClicked(int position) {
		SectionItem item = sectionItems.get(position);
		String module = item.getModule();
		Intent intent = new Intent(getActivity(), SectionItemType.valueOf(module.toUpperCase()).getClazz());
		intent.putExtra("title", item.getName());
		startActivity(intent);
	}
}
