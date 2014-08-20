package tv.turbik.client;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 12:02
 */
public class Images {

	public static final double SERIES_BIG_POSTER_ASPECT = 980.0 / 335.0;

	private static final String BASE_URL = "http://s." + TurboFilmClient.DOMAIN + "/i/";

	private static final String SMALL_SQUARE = "%sseries/%d.png";
	private static final String MID_POSTER = "%sseries/%ds.jpg";
	private static final String BIG_POSTER = "%sseries/%dts.jpg";

	public static String seriesSmallSquare(short id) {
		return String.format(SMALL_SQUARE, BASE_URL, id);
	}

	public static String seriesMidPoster(short id) {
		return String.format(MID_POSTER, BASE_URL, id);
	}

	public static String seriesBigPoster(short id) {
		return String.format(BIG_POSTER, BASE_URL, id);
	}

}
