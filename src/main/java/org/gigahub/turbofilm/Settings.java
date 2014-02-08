package org.gigahub.turbofilm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import javax.inject.Singleton;

/**
 * @author Pavel Savinov
 * @version 13/12/13 15:19
 */
@Singleton
public class Settings {

	private SharedPreferences preferences;

	public void init(Context context) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public void saveIdCookie(Cookie cookie) {
		String string = new StringBuilder()
				.append(cookie.getName()).append("===")
				.append(cookie.getValue()).append("===")
				.append(cookie.getDomain()).append("===")
				.append(cookie.getPath())
				.toString();
		preferences.edit().putString("cookie.ias_id", string).commit();
	}

	public Cookie loadCookie() {
		String[] parts = preferences.getString("cookie.ias_id", "").split("===");
		if (parts.length > 1) {
			BasicClientCookie cookie = new BasicClientCookie(parts[0], parts[1]);
			cookie.setDomain(parts[2]);
			cookie.setPath(parts[3]);
			return cookie;
		} else {
			return null;
		}
	}

}
