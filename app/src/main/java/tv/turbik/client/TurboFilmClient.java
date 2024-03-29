package tv.turbik.client;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.turbik.Settings_;
import tv.turbik.client.episode.EpisodePage;
import tv.turbik.client.home.HomePage;
import tv.turbik.client.season.SeasonPage;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.exception.server.NotLoggedInException;
import tv.turbik.client.exception.server.ServerException;
import tv.turbik.client.episode.EpisodePageParser;
import tv.turbik.client.home.HomePageParser;
import tv.turbik.client.season.SeasonPageParser;
import tv.turbik.client.series.SeriesPage;
import tv.turbik.client.series.SeriesPageParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 25.08.13 22:40
 */
@EBean(scope = EBean.Scope.Singleton)
public class TurboFilmClient {

	public static final String DOMAIN = "turbik.tv";

	private static final Logger L = LoggerFactory.getLogger(TurboFilmClient.class.getSimpleName());
	private static final String BASE_URL = "https://" + DOMAIN + "/";

	private DefaultHttpClient httpClient = new DefaultHttpClient();

	@Pref Settings_ settings;

	public void saveIdCookie(Cookie cookie) {
		String string = new StringBuilder()
				.append(cookie.getName()).append("===")
				.append(cookie.getValue()).append("===")
				.append(cookie.getDomain()).append("===")
				.append(cookie.getPath())
				.toString();
		settings.ias_id().put(string);
	}

	public Cookie loadCookie() {
		String[] parts = settings.ias_id().get().split("===");
		if (parts.length > 1) {
			BasicClientCookie cookie = new BasicClientCookie(parts[0], parts[1]);
			cookie.setDomain(parts[2]);
			cookie.setPath(parts[3]);
			return cookie;
		} else {
			return null;
		}
	}

	public Cookie getCookie() {
		for (Cookie cookie : httpClient.getCookieStore().getCookies()) {
			if (cookie.getName().equals("IAS_ID")) return cookie;
		}
		return null;
	}

	public void addCookie(Cookie cookie) {
		httpClient.getCookieStore().addCookie(cookie);
	}

	public void singin(String login, String password) throws TurboException {

		HttpPost post = new HttpPost(BASE_URL + "Signin");

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("login", login));
		list.add(new BasicNameValuePair("passwd", password));
		list.add(new BasicNameValuePair("remember", "on"));

		try {
			post.setEntity(new UrlEncodedFormEntity(list));

			HttpResponse response = httpClient.execute(post);

			String text = IOUtils.toString(response.getEntity().getContent());

			if (text.contains("Только верующий сможет пройти"))
				throw new NotLoggedInException();
		} catch (IOException e) {
			throw new ServerException(e);
		}

	}

	public HomePage getHomePage() throws TurboException {
		HttpGet get = new HttpGet(BASE_URL);
		return new HomePageParser().parse(executeRequest(get));
	}

	public SeriesPage getSeriesPage() throws TurboException {
		HttpGet get = new HttpGet(BASE_URL + "Series");
		return new SeriesPageParser().parse(executeRequest(get));
	}

	public SeasonPage getSeasonPage(String alias, byte season) throws TurboException {
		HttpGet get = new HttpGet(BASE_URL + "Series/" + alias + "/Season" + season);
		return new SeasonPageParser().parse(executeRequest(get));
	}

	public EpisodePage getEpisodePage(String alias, byte season, byte episode) throws TurboException {
		HttpGet get = new HttpGet(BASE_URL + "Watch/" + alias + "/Season" + season + "/Episode" + episode);
		return new EpisodePageParser().parse(executeRequest(get));
	}

	private String executeRequest(HttpGet get) throws ServerException {

		try {
			HttpResponse response = httpClient.execute(get);

			String text = IOUtils.toString(response.getEntity().getContent());

			if (text.contains("Только верующий сможет пройти"))
				throw new NotLoggedInException();

			return text;
		} catch (IOException e) {
			throw new ServerException(e);
		}

	}

}