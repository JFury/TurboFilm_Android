package ru.swapii.turbofilm;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import ru.swapii.turbofilm.model.Episode;
import ru.swapii.turbofilm.model.Series;
import ru.swapii.turbofilm.parsers.SeriesParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 05.08.12
 * Time: 21:54
 */
public class TurboFilm {

	private static final String SITE = "http://turbofilm.tv/";
	private HttpClient client;

	private String login;
	private String password;

	public TurboFilm() {
		client = new DefaultHttpClient();
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public boolean login() {

		HttpPost post = new HttpPost(SITE + "Signin");

		post.getParams().setBooleanParameter("http.protocol.handle-redirects", false);

		List <NameValuePair> pairs = new ArrayList <NameValuePair>();
		pairs.add(new BasicNameValuePair("login", login));
		pairs.add(new BasicNameValuePair("passwd", password));
		pairs.add(new BasicNameValuePair("remember", "on"));

		try {
			post.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
			HttpResponse response = client.execute(post);

			response.getEntity().getContent().close();

			return response.getStatusLine().getStatusCode() == 302;
//			return response.getStatusLine().getStatusCode() == 302 && response.getFirstHeader("Location").getValue().equals("http://turbofilm.tv/Series");

		} catch (UnsupportedEncodingException e) {
			return false;
		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}

		return false;
	}

	public List<Series> getSeries() throws IOException, NotLoggedInException {

		HttpGet get = new HttpGet(SITE + "Series");

		get.getParams().setBooleanParameter("http.protocol.handle-redirects", false);

		HttpResponse response = client.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();

		String page = IOUtils.toString(response.getEntity().getContent());

		if (statusCode == 200) {

			return SeriesParser.parse(page);

		} else if (statusCode == 302 && response.getFirstHeader("location").getValue().equals("/Signin")) {

			throw new NotLoggedInException();
		}

		return new ArrayList<Series>();
	}

	public List<Episode> getEpisodes(String urlPath, int seasonNumber) {

		return null;
	}

}
