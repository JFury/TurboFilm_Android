package ru.swapii.turbofilm;

import android.util.Base64;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 16.08.12
 * Time: 12:56
 */
public class MetaDecoder {

	static char[] loc6 = {'2','I','0','=','3','Q','8','V','7','X','G','M','R','U','H','4','1','Z','5','D','N','6','L','9','B','W'};
	static char[] loc7 = {'x','u','Y','o','k','n','g','r','m','T','w','f','d','c','e','s','i','l','y','t','p','b','z','a','J','v'};

	public static String decode(String data) {

		data = data.replace("%2b", "+");
		data = data.replace("%3d", "=");
		data = data.replace("%2f", "/");

		for (int i = 0; i < loc7.length; i++) {
			char c7 = loc7[i];
			char c6 = loc6[i];
			data = data.replace(String.valueOf(c7), "___");
			data = data.replace(String.valueOf(c6), String.valueOf(c7));
			data = data.replace("___", String.valueOf(c6));
		}

		return new String(Base64.decode(data, Base64.DEFAULT));
	}

}