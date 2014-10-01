package tv.turbik;

/**
 * Created by swap_i on 14/02/14.
 */
public class Utils {

	public static final String POSITIVE_HOURS_TIME_FORMAT = " %02d:%02d:%02d";
	public static final String NEGATIVE_HOURS_TIME_FORMAT = "-%02d:%02d:%02d";

	public static final String POSITIVE_TIME_FORMAT = " %02d:%02d";
	public static final String NEGATIVE_TIME_FORMAT = "-%02d:%02d";

	public static String convertTime(int duration) {
		boolean positive = duration >= 0;

		duration = Math.abs(duration);

		duration /= 1000;

		int h = duration / 3600;
		duration -= h * 3600;

		int m = duration / 60;
		duration -= m * 60;

		int s = duration;

		if (h > 0) {
			return String.format(positive ? POSITIVE_HOURS_TIME_FORMAT : NEGATIVE_HOURS_TIME_FORMAT, h, m, s);
		} else {
			return String.format(positive ? POSITIVE_TIME_FORMAT : NEGATIVE_TIME_FORMAT, m, s);
		}
	}

}
