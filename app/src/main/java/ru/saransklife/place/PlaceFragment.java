package ru.saransklife.place;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import ru.saransklife.R;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_place)
public class PlaceFragment extends Fragment {

	private FragmentManager fragmentManager;

	@AfterViews
	void afterViews(){
		fragmentManager = getChildFragmentManager();

		fragmentManager.beginTransaction()
				.replace(R.id.container, new CategoriesFragment_())
				.commit();
	}

}
