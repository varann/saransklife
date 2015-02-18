package ru.saransklife.client;

import android.content.Context;
import android.database.Cursor;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by asavinova on 19/02/15.
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataHelper {

	@Bean Dao dao;

	public Cursor getPlaceCategoriesCursor(boolean force, boolean afterUpdate, Context context) {
		Cursor cursor = dao.getPlaceCategoryCursor();

		if (afterUpdate) {
			return cursor;
		}

		if (force || cursor.getCount() == 0) {
			DataService_.intent(context).placeCategoriesAction().start();
		}

		return cursor;
	}
}
