package tv.turbik.client.parser;

import tv.turbik.client.exception.client.ParseException;
import tv.turbik.client.TurbikClient;
import tv.turbik.client.container.ToolbarSeriesContainer;
import tv.turbik.client.model.BasicSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 22.11.13 13:40
 */
public class ToolbarSeriesParser extends Parser {

	private static final Pattern POPUP_PATTERN = Pattern.compile("id=\"topseries\"(.*?)                   </div>", Pattern.DOTALL);
	private static final Pattern MY_SERIES_PATTERN = Pattern.compile("class=\"topmyseries\">Мои сериалы</div>(.*?)</div>", Pattern.DOTALL);
	private static final Pattern OTHER_SERIES_PATTERN = Pattern.compile("class=\"topmyseries\">Все сериалы</div>(.*?)</div>", Pattern.DOTALL);

	private static final Pattern SERIES_PATTERN = Pattern.compile("<a href.*?</a>", Pattern.DOTALL);
	private static final Pattern ID_PATTERN = Pattern.compile("<img src=\"//s." + TurbikClient.DOMAIN + "/i/series/([0-9]*?).png\"");
	private static final Pattern ALIAS_PATTERN = Pattern.compile("<a href=\"/Series/(.*?)\">");
	private static final Pattern NAME_EN_PATTERN = Pattern.compile("<span class=\"en\">(.*?)</span>");
	private static final Pattern NAME_RU_PATTERN = Pattern.compile("<span class=\"ru\">(.*?)</span>");


	public ToolbarSeriesContainer parse(String text) throws ParseException {

		Matcher popupMatcher = POPUP_PATTERN.matcher(text);

		if (!popupMatcher.find()) throw new ParseException("Can't parse toolbar series");

		String popupText = popupMatcher.group();

		ToolbarSeriesContainer container = new ToolbarSeriesContainer();

		List<BasicSeries> mySeries = parseSeriesList(MY_SERIES_PATTERN, popupText);
		container.getMySeries().addAll(mySeries);

		List<BasicSeries> otherSeries = parseSeriesList(OTHER_SERIES_PATTERN, popupText);
		container.getOtherSeries().addAll(otherSeries);

		return container;
	}

	private List<BasicSeries> parseSeriesList(Pattern pattern, String text) throws ParseException {

		Matcher matcher = pattern.matcher(text);

		if (!matcher.find()) throw new ParseException("Can't parse toolbar series");


		String listText = matcher.group();

		List<BasicSeries> result = new ArrayList<BasicSeries>();

		Matcher oneSeriesMatcher = SERIES_PATTERN.matcher(listText);
		while (oneSeriesMatcher.find()) {
			BasicSeries series = parseOneSeries(oneSeriesMatcher.group());
			result.add(series);
		}

		return result;
	}

	private BasicSeries parseOneSeries(String text) throws ParseException {
		BasicSeries series = new BasicSeries();
		series.setId(Integer.parseInt(getString(text, ID_PATTERN)));
		series.setAlias(getString(text, ALIAS_PATTERN));
		series.setNameEn(getString(text, NAME_EN_PATTERN));
		series.setNameRu(getString(text, NAME_RU_PATTERN));
		return series;
	}

}