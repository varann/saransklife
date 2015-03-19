package ru.saransklife.client.event.info;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.saransklife.R;

/**
 * Created by asavinova on 18/03/15.
 */
@EViewGroup(R.layout.seance_date_view)
public class DateView extends LinearLayout {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM");
	private SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE");

	@ViewById TextView dateView;
	@ViewById TextView dayOfWeekView;
	private Date date;

	public DateView(Context context) {
		super(context);
	}

	public DateView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setDate(Date date) {
		this.date = date;
		dateView.setText(dateFormat.format(date));
		dayOfWeekView.setText(StringUtils.capitalize(dayOfWeekFormat.format(date)));
	}

	public Date getDate() {
		return date;
	}

	public void setSelectedView(boolean selected) {
		setBackgroundResource(selected ? R.color.seance_selected : R.color.white);
	}
}
