package org.gigahub.turbofilm.client.parser;

import org.gigahub.turbofilm.client.ParseException;
import org.gigahub.turbofilm.client.container.SeasonPage;
import org.gigahub.turbofilm.client.model.BasicEpisode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 13:32
 */
public class SeasonPageParser extends Parser {

	private static final Pattern SEASONS = Pattern.compile("<div class=\"seasonnum\">(.*?)</div>", Pattern.DOTALL);
	private static final Pattern LAST_SEASON = Pattern.compile("<span class=\".*?\">Сезон ([0-9]*?)</span>");
	private static final Pattern CURRENT_SEASON = Pattern.compile("<span class=\"seasonnumactive\">Сезон ([0-9]*?)</span>");

	private static final Pattern SERIES_NAME_EN = Pattern.compile("<span class=\"sseriestitleten\">(.*?)</span>");
	private static final Pattern SERIES_NAME_RU = Pattern.compile("<span class=\"sseriestitleten\">.*?</span> / (.*?)</span>");

	private static final Pattern EPISODES = Pattern.compile("<div class=\"sserieslistbox\">(.*?)</div>", Pattern.DOTALL);
	private static final Pattern EPISODE = Pattern.compile("<a href(.*?)</a>", Pattern.DOTALL);

	private static final Pattern POSTER = Pattern.compile("<img src=\"//img.turbik.tv/.*?/(.*?)a.jpg\"");

	private static final Pattern HQ = Pattern.compile("<span class=\"sserieshq\"></span>");
	private static final Pattern AUDIO_EN = Pattern.compile("<span class=\"sseriesesound\"></span>");
	private static final Pattern AUDIO_RU = Pattern.compile("<span class=\"sseriesrsound\"></span>");
	private static final Pattern SUB_EN = Pattern.compile("<span class=\"sseriesesub\"></span>");
	private static final Pattern SUB_RU = Pattern.compile("<span class=\"sseriesrsub\"></span>");

	private static final Pattern NAME_EN = Pattern.compile("<span class=\"sserieslistonetxten\">(.*?)</span>");
	private static final Pattern NAME_RU = Pattern.compile("<span class=\"sserieslistonetxtru\">(.*?)</span>");

	private static final Pattern EPISODE_SEASON = Pattern.compile("<span class=\"sserieslistonetxtse\">Сезон: ([0-9]+?)</span>");
	private static final Pattern EPISODE_EPISODE = Pattern.compile("<span class=\"sserieslistonetxtep\">Эпизод: ([0-9]+?)</span>");

	public SeasonPage parse(String text) throws ParseException {

		SeasonPage seasonPage = new SeasonPage();

		Matcher nameEnMatcher = SERIES_NAME_EN.matcher(text);
		nameEnMatcher.find();
		seasonPage.setSeriesNameEn(nameEnMatcher.group(1));

		Matcher nameRuMatcher = SERIES_NAME_RU.matcher(text);
		nameRuMatcher.find();
		seasonPage.setSeriesNameRu(nameRuMatcher.group(1));

		parseSeasons(text, seasonPage);
		parseEpisodes(text, seasonPage);

		return seasonPage;
	}

	private void parseSeasons(String text, SeasonPage seasonPage) throws ParseException {

		Matcher matcher = SEASONS.matcher(text);

		if (!matcher.find()) throw new ParseException();

		String seasonsText = matcher.group();


		Matcher lastMatcher = LAST_SEASON.matcher(seasonsText);
		if (!lastMatcher.find()) throw new ParseException();
		seasonPage.setSeasonsCount(Integer.parseInt(lastMatcher.group(1)));

		Matcher currentMatcher = CURRENT_SEASON.matcher(seasonsText);
		if (!currentMatcher.find()) throw new ParseException();
		seasonPage.setCurrentSeason(Integer.parseInt(currentMatcher.group(1)));

	}

	private void parseEpisodes(String text, SeasonPage seasonPage) throws ParseException {

		Matcher matcher = EPISODES.matcher(text);
		if (!matcher.find()) throw new ParseException();

		String episodesText = matcher.group();

		Matcher oneEpisodeMatcher = EPISODE.matcher(episodesText);
		while (oneEpisodeMatcher.find()) {
			seasonPage.getEpisodes().add(parseOneEpisode(oneEpisodeMatcher.group()));
		}

	}

	private BasicEpisode parseOneEpisode(String s) throws ParseException {
		BasicEpisode e = new BasicEpisode();

		e.setSeason(Integer.parseInt(getString(s, EPISODE_SEASON)));
		e.setEpisode(Integer.parseInt(getString(s, EPISODE_EPISODE)));

		e.setPosterHash(getString(s, POSTER));

		e.setNameEn(getString(s, NAME_EN));
		e.setNameRu(getString(s, NAME_RU));

		e.setHasHQ(HQ.matcher(s).find());
		e.setHasEnAudio(AUDIO_EN.matcher(s).find());
		e.setHasRuAudio(AUDIO_RU.matcher(s).find());
		e.setHasEnSubs(SUB_EN.matcher(s).find());
		e.setHasRuSubs(SUB_RU.matcher(s).find());

		return e;
	}

}
