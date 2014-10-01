package tv.turbik.client.toolbar.series;

import tv.turbik.client.Parser;
import tv.turbik.client.TurboFilmClient;
import tv.turbik.client.exception.client.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 22.11.13 13:40
 */
public class ToolbarSeriesParser extends Parser {

	private static final Pattern TOP_SERIES_PATTERN = Pattern.compile("id=\"topseries\"(.*?)class=\"content\"", Pattern.DOTALL);
	private static final Pattern MY_SERIES_PATTERN = Pattern.compile("class=\"topmyseries\">Мои сериалы</div>(.*?)</div>", Pattern.DOTALL);
	private static final Pattern OTHER_SERIES_PATTERN = Pattern.compile("class=\"topmyseries\">Все сериалы</div>(.*?)</div>", Pattern.DOTALL);

	private static final Pattern SERIES_PATTERN = Pattern.compile("<a href.*?</a>", Pattern.DOTALL);
	private static final Pattern ID_PATTERN = Pattern.compile("<img src=\"//s." + TurboFilmClient.DOMAIN + "/i/series/([0-9]*?).png\"");
	private static final Pattern ALIAS_PATTERN = Pattern.compile("<a href=\"/Series/(.*?)\">");
	private static final Pattern NAME_EN_PATTERN = Pattern.compile("<span class=\"en\">(.*?)</span>");
	private static final Pattern NAME_RU_PATTERN = Pattern.compile("<span class=\"ru\">(.*?)</span>");


	public ToolbarSeriesContainer parse(String text) throws ParseException {

		Matcher popupMatcher = TOP_SERIES_PATTERN.matcher(text);

		if (!popupMatcher.find()) throw new ParseException("Can't parse toolbar series");

		String popupText = popupMatcher.group();

		ToolbarSeriesContainer container = new ToolbarSeriesContainer();

		List<ToolbarSeries> mySeries = parseSeriesList(MY_SERIES_PATTERN, popupText);
		container.getMySeries().addAll(mySeries);

		List<ToolbarSeries> otherSeries = parseSeriesList(OTHER_SERIES_PATTERN, popupText);
		container.getOtherSeries().addAll(otherSeries);

		return container;
	}

	private List<ToolbarSeries> parseSeriesList(Pattern pattern, String text) throws ParseException {

		Matcher matcher = pattern.matcher(text);

		if (!matcher.find()) throw new ParseException("Can't parse toolbar series");


		String listText = matcher.group();

		List<ToolbarSeries> result = new ArrayList<ToolbarSeries>();

		Matcher oneSeriesMatcher = SERIES_PATTERN.matcher(listText);
		while (oneSeriesMatcher.find()) {
			ToolbarSeries series = parseOneSeries(oneSeriesMatcher.group());
			result.add(series);
		}

		return result;
	}

	private ToolbarSeries parseOneSeries(String text) throws ParseException {
		ToolbarSeries series = new ToolbarSeries();
		series.setId(Short.parseShort(parseString(text, ID_PATTERN)));
		series.setAlias(parseString(text, ALIAS_PATTERN));
		series.setNameEn(parseString(text, NAME_EN_PATTERN));
		series.setNameRu(parseString(text, NAME_RU_PATTERN));
		return series;
	}

}