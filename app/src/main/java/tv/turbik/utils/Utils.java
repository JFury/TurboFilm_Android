package tv.turbik.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by pavel on 25/10/14.
 */
public final class Utils {

	private Utils() {
	}

	public static String sha1(String source) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(source.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error calculating SHA1", e);
		}
	}

}
