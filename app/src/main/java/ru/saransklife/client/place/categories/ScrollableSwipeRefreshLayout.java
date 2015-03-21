package ru.saransklife.client.place.categories;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

import ru.saransklife.R;

/**
 * Created by asavinova on 21/03/15.
 */
public class ScrollableSwipeRefreshLayout extends SwipeRefreshLayout {

	public ScrollableSwipeRefreshLayout(Context context) {
		super(context);
	}

	public ScrollableSwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean canChildScrollUp() {
		// Condition to check scrollview reached at top while scrolling
		View view = findViewById(R.id.grid);
		if (view == null) {
			return super.canChildScrollUp();
		} else {
			return view.canScrollVertically(-1);
		}
	}
}
