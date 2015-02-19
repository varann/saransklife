package ru.saransklife.client;

import android.content.Context;
import android.database.Cursor;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Date;

/**
 * Created by asavinova on 19/02/15.
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataHelper {

	@Bean Dao dao;

	public Cursor getPlaceCategoriesCursor(boolean force, Context context) {
		Cursor cursor = dao.getPlaceCategoryCursor();

		if (force || needUpdate(Dao.Request.PLACE_CATEGORIES)) {
			DataService_.intent(context).placeCategoriesAction().start();
		}

		return cursor;
	}

	private boolean needUpdate(Dao.Request request) {
		Date lastUpdated = dao.getLastUpdated(request);

		if (lastUpdated == null) return true;

		Date current = new Date();
		long minutes = (current.getTime() - lastUpdated.getTime()) / 1000 / 60;
		if (minutes < 60) {
			return false;
		} else {
			return true;
		}
	}
}
