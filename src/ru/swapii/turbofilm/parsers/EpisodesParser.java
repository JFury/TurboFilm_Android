package ru.swapii.turbofilm.parsers;

import ru.swapii.turbofilm.model.Episode;
import ru.swapii.turbofilm.model.Series;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 16.08.12
 * Time: 23:29
 */
public class EpisodesParser {

	public static List<Episode> parse(String source) {

		Pattern allEpisodesPattern = Pattern.compile("<div class=\"sserieslistbox\">(.*?)<div class=\"sseriesrightbox\">", Pattern.DOTALL);
		Pattern episodePattern = Pattern.compile("\\s<a href=\"(.*?)\">\\s(.*?)</a>", Pattern.DOTALL);

		Matcher matcher = allEpisodesPattern.matcher(source);

		matcher.find();

		ArrayList<Episode> list = new ArrayList<Episode>();

		Matcher episodeMatcher = episodePattern.matcher(matcher.group());
		while (episodeMatcher.find()) {

			Episode episode = new Episode();
			episode.setPath(episodeMatcher.group(1));

			String s = episodeMatcher.group(2);

			episode.setCoverSmallPath(find(s, "<img src=\"(.*?)\" width=\"170\" height=\"96\" alt=\".*?\" />"));

			episode.setNameEn(find(s, "<span class=\"sserieslistonetxten\">(.*?)</span>"));
			episode.setNameRu(find(s, "<span class=\"sserieslistonetxtru\">(.*?)</span>"));

			episode.setSeason(Integer.parseInt(find(s, "<span class=\"sserieslistonetxtse\">Сезон: (\\d*?)</span>")));
			episode.setEpisode(Integer.parseInt(find(s, "<span class=\"sserieslistonetxtep\">Эпизод: (\\d*?)</span>")));

			list.add(episode);
		}

		Collections.reverse(list);

		return list;
	}

	private static String find(String seriesItemSource, String regex) {
		Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(seriesItemSource);
		matcher.find();
		return matcher.group(1);
	}

}
