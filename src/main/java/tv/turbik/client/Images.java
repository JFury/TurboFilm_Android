package tv.turbik.client;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 12:02
 */
public class Images {

	public static final double SERIES_BIG_POSTER_ASPECT = 980.0 / 335.0;

	private static final String BASE_URL = "http://s." + TurboFilmClient.DOMAIN + "/i/";

	public static String seriesSmallSquare(int id) {
		return BASE_URL + "series/" + id + ".png";
	}

	public static String seriesMidPoster(int id) {
		return BASE_URL + "series/" + id + "s.jpg";
	}

	public static String seriesBigPoster(int id) {
		return BASE_URL + "series/" + id + "ts.jpg";
	}

}
