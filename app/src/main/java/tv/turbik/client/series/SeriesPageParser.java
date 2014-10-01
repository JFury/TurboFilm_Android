package tv.turbik.client.series;

import tv.turbik.client.Parser;
import tv.turbik.client.exception.client.ParseException;
import tv.turbik.client.toolbar.ToolbarParser;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pavel Savinov
 * @version 19/08/14 00:23
 */
public class SeriesPageParser extends Parser {

	private static final Pattern SERIES_PATTERN = Pattern.compile("<div class=\"content\">(.*?)</div>", Pattern.DOTALL);
	private static final Pattern SERIES_ITEM_PATTERN = Pattern.compile("<a href=\"/Series/.*?\">.*?</a>", Pattern.DOTALL);

	private static final Pattern ID_PATTERN = Pattern.compile("<img src=\"//s.turbik.tv/i/series/(.*?)s.jpg\" width=\"370\" height=\"125\"", Pattern.DOTALL);
	private static final Pattern ALIAS_PATTERN = Pattern.compile("<a href=\"/Series/(.*?)\">", Pattern.DOTALL);
	private static final Pattern NAME_EN_PATTERN = Pattern.compile("<span class=\"serieslistboxen\">(.*?)</span>", Pattern.DOTALL);
	private static final Pattern NAME_RU_PATTERN = Pattern.compile("<span class=\"serieslistboxru\">(.*?)</span>", Pattern.DOTALL);
	private static final Pattern SEASONS_PATTERN = Pattern.compile("<span class=\"serieslistboxperstext\">Сезонов: (.*?)</span>", Pattern.DOTALL);
	private static final Pattern EPISODES_PATTERN = Pattern.compile("<span class=\"serieslistboxperstext\">Эпизодов: (.*?)</span>", Pattern.DOTALL);
	private static final Pattern DURATION_PATTERN = Pattern.compile("<span class=\"serieslistboxperstext\">Продолжительность серии: (.*?) минут", Pattern.DOTALL);
	private static final Pattern PREMIER_PATTERN = Pattern.compile("<span class=\"serieslistboxperstext\">Премьера сериала:(.*?)</span>", Pattern.DOTALL);
	private static final Pattern GENRES_PATTERN = Pattern.compile("<span class=\"serieslistboxperstext\">Жанр:(.*?)</span>", Pattern.DOTALL);
	private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("<span class=\"serieslistboxdesc\">(.*?)</span>", Pattern.DOTALL);

	public SeriesPage parse(String pageSource) throws ParseException {
		SeriesPage page = new SeriesPage();

		page.setToolbarContainer(new ToolbarParser().parse(pageSource));

		Matcher allSeriesMatcher = SERIES_PATTERN.matcher(pageSource);

		if (!allSeriesMatcher.find()) throw new ParseException("Can't find series container");

		String allSeriesSource = allSeriesMatcher.group(1);

		Matcher seriesItemMatcher = SERIES_ITEM_PATTERN.matcher(allSeriesSource);

		while (seriesItemMatcher.find()) {
			String seriesItemSource = seriesItemMatcher.group();
			page.getSeriesList().add(parseSeriesItem(seriesItemSource));
		}

		return page;
	}

	private SeriesPageSeries parseSeriesItem(String source) throws ParseException {
		SeriesPageSeries series = new SeriesPageSeries();
		series.setId(Short.parseShort(parseString(source, ID_PATTERN)));
		series.setAlias(parseString(source, ALIAS_PATTERN));
		series.setNameEn(parseString(source, NAME_EN_PATTERN));
		series.setNameRu(parseString(source, NAME_RU_PATTERN));
		series.setSeasonsCount(Byte.parseByte(parseString(source, SEASONS_PATTERN)));
		series.setEpisodesCount(Short.parseShort(parseString(source, EPISODES_PATTERN)));
		series.setEpisodeDuration(parseInt(source, DURATION_PATTERN));
		series.setPremierDate(parseString(source, PREMIER_PATTERN));
		series.setShortDescription(parseString(source, DESCRIPTION_PATTERN));
		series.setGenres(parseGenres(source));
		return series;
	}

	private Set<String> parseGenres(String source) throws ParseException {

		String genresSource = parseString(source, GENRES_PATTERN);

		String[] parts = genresSource.split(",");

		TreeSet<String> genres = new TreeSet<String>();

		for (String part : parts) {
			genres.add(part.trim().toLowerCase());
		}

		return genres;
	}

}
