package ru.saransklife.client.reference;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import ru.saransklife.R;
import ru.saransklife.client.BaseActivity;
import ru.saransklife.client.Dao;
import ru.saransklife.client.ui.AwesomeIconTextView;
import ru.saransklife.client.ui.DescriptionView;
import ru.saransklife.client.ui.TitleView;
import ru.saransklife.dao.Reference;

/**
 * Created by asavinova on 07/02/15.
 */
@EActivity(R.layout.activity_reference_info)
public class ReferenceInfoActivity extends BaseActivity {

	@ViewById Toolbar toolbar;
	@ViewById TitleView titleView;
	@ViewById DescriptionView descriptionView;
	@ViewById AwesomeIconTextView addressView;
	@ViewById AwesomeIconTextView phoneView;
	@ViewById AwesomeIconTextView websiteView;
	@ViewById TextView information;

	@Bean Dao dao;
	@Extra long id;

	private Reference reference;

	@AfterViews
	void afterViews() {
		logExtra(new String[]{"id"}, Long.toString(id));

		reference = dao.getReference(id);
		toolbar.setTitle(reference.getName());
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		titleView.setTitle(reference.getName());
		descriptionView.setText(reference.getDescription());

		setTextWithIcon(addressView, R.string.map_marker, reference.getAddress());
		setTextWithIcon(phoneView, R.string.phone, reference.getPhone());
		setTextWithIcon(websiteView, R.string.globe, reference.getSite());

		String text = reference.getInformation();
		information.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		information.setText(text);
	}

	private void setTextWithIcon(TextView view, int icon, String text) {
		view.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		view.setText(TextUtils.isEmpty(text) ? "" : getString(icon) + " " + text);
	}

}
