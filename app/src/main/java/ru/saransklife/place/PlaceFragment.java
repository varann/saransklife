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

		CategoriesFragment categoriesFragment = new CategoriesFragment_();
		categoriesFragment.setListener(new CategoriesFragmentListener());

		fragmentManager.beginTransaction()
				.addToBackStack(null)
				.replace(R.id.container, categoriesFragment)
				.commit();
	}

	class CategoriesFragmentListener {

		public void onCategorySelected(long id) {
			EntitiesByCategoryFragment entitiesFragment =
					EntitiesByCategoryFragment_.builder().
					categoryId(id).
					build();

			fragmentManager.beginTransaction()
					.addToBackStack(null)
					.replace(R.id.container, entitiesFragment)
					.commit();
		}
	}

}
