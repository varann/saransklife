package ru.saransklife.place;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_entities_by_category)
public class EntitiesByCategoryFragment extends Fragment {

	@FragmentArg
	long categoryId;

	@ViewById
	TextView text;

	@AfterViews
	void afterViews() {
		text.setText("Category id = " + categoryId);
	}

}
