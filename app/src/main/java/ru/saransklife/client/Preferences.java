package ru.saransklife.client;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by asavinova on 17/10/14.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface Preferences {

	@DefaultBoolean(false)
	boolean navigationDrawerLearned();

}
