package ru.swapii.turbofilm.parsers;

import ru.swapii.turbofilm.model.Series;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 16.08.12
 * Time: 23:29
 */
public class SeriesParser {

	public static List<Series> parse(String source) {

		Pattern allSeriesPattern = Pattern.compile("<div id=\"series\">(.*?)<div id=\"footer\">", Pattern.DOTALL);
		Pattern seriesPattern = Pattern.compile("\\s<a href=\"/Series/(.*?)\">\\s(.*?)</a>", Pattern.DOTALL);

		Matcher matcher = allSeriesPattern.matcher(source);

		matcher.find();

		ArrayList<Series> list = new ArrayList<Series>();

		Matcher seriesMatcher = seriesPattern.matcher(matcher.group());
		while (seriesMatcher.find()) {

			Series series = new Series();
			series.setPath(seriesMatcher.group(1));

			String s = seriesMatcher.group(2);
			series.setId(Integer.parseInt(find(s, "<img src=\"http://s.turbofilm.tv/i/series/(\\d*?)s.jpg\" width=\"370\" height=\"125\" alt=\".*?\" />")));
			series.setNameRu(find(s, "<span class=\"serieslistboxru\">(.*?)</span>"));
			series.setNameEn(find(s, "<span class=\"serieslistboxen\">(.*?)</span>"));
			series.setSeasonsCount(Integer.parseInt(find(s, "<span class=\"serieslistboxperstext\">Сезонов: (\\d*?)</span>")));
			series.setEpisodesCount(Integer.parseInt(find(s, "<span class=\"serieslistboxperstext\">Эпизодов: (\\d*?)</span>")));
			series.setDescription(find(s, "<span class=\"serieslistboxdesc\">(.*?)</span>"));

			list.add(series);
		}

		return list;
	}

	private static String find(String seriesItemSource, String regex) {
		Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(seriesItemSource);
		matcher.find();
		return matcher.group(1);
	}

}
