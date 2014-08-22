package org.gigahub.turbofilm;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * @author Pavel Savinov
 * @version 13/12/13 15:19
 */
@SharedPref(SharedPref.Scope.APPLICATION_DEFAULT)
public interface Settings {

	public String ias_id();

}
