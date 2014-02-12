package org.gigahub.turbofilm.client;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.gigahub.turbofilm.client.container.EpisodePage;
import org.gigahub.turbofilm.client.container.HomePage;
import org.gigahub.turbofilm.client.container.SeasonPage;
import org.gigahub.turbofilm.client.container.ToolbarSeriesContainer;
import org.gigahub.turbofilm.client.parser.EpisodePageParser;
import org.gigahub.turbofilm.client.parser.SeasonPageParser;
import org.gigahub.turbofilm.client.parser.ToolbarSeriesParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 25.08.13 22:40
 */
public class TurboFilmClient {

	public static final String DOMAIN = "turbik.tv";

	private static final Logger L = LoggerFactory.getLogger(TurboFilmClient.class);
	private static final String BASE_URL = "https://" + DOMAIN + "/";

	private DefaultHttpClient httpClient = new DefaultHttpClient();

	public Cookie getCookie() {
		for (Cookie cookie : httpClient.getCookieStore().getCookies()) {
			if (cookie.getName().equals("IAS_ID")) return cookie;
		}
		return null;
	}

	public void addCookie(Cookie cookie) {
		httpClient.getCookieStore().addCookie(cookie);
	}

	public void singin(String login, String password) throws NotLoggedInException, IOException {

		HttpPost post = new HttpPost(BASE_URL + "Signin");

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("login", login));
		list.add(new BasicNameValuePair("passwd", password));
		list.add(new BasicNameValuePair("remember", "on"));
		post.setEntity(new UrlEncodedFormEntity(list));

		HttpResponse response = httpClient.execute(post);

		String text = IOUtils.toString(response.getEntity().getContent());

		if (text.contains("Только верующий сможет пройти"))
			throw new NotLoggedInException();

	}

	public HomePage getHome() throws NotLoggedInException, IOException, ParseException {

		HttpGet get = new HttpGet(BASE_URL);
		HttpResponse response = httpClient.execute(get);

		String text = IOUtils.toString(response.getEntity().getContent());

		if (text.contains("Только верующий сможет пройти"))
			throw new NotLoggedInException();

		HomePage container = new HomePage();

		ToolbarSeriesContainer series = new ToolbarSeriesParser().parse(text);

		container.setTopSeries(series);

		return container;
	}

	public SeasonPage getSeason(String alias, int season) throws NotLoggedInException, IOException, ParseException {

		HttpGet get = new HttpGet(BASE_URL + "Series/" + alias + "/Season" + season);
		HttpResponse response = httpClient.execute(get);

		String text = IOUtils.toString(response.getEntity().getContent());

		if (text.contains("Только верующий сможет пройти"))
			throw new NotLoggedInException();

		return new SeasonPageParser().parse(text);
	}

	public EpisodePage getEpisode(String alias, int season, int episode) throws NotLoggedInException, IOException, ParseException {

		HttpGet get = new HttpGet(BASE_URL + "Watch/" + alias + "/Season" + season + "/Episode" + episode);
		HttpResponse response = httpClient.execute(get);

		String text = IOUtils.toString(response.getEntity().getContent());

		if (text.contains("Только верующий сможет пройти"))
			throw new NotLoggedInException();

		return new EpisodePageParser().parse(text);
	}

}