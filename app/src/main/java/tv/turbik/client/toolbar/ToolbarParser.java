package tv.turbik.client.toolbar;

import tv.turbik.client.exception.client.ParseException;
import tv.turbik.client.toolbar.series.ToolbarSeriesParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pavel Savinov
 * @version 19/08/14 19:49
 */
public class ToolbarParser {

	private static final Pattern TOOLBAR_PATTERN = Pattern.compile("<div class=\"head-line\">(.*?)<div class=\"content\">", Pattern.DOTALL);

	public ToolbarContainer parse(String pageSource) throws ParseException {
		ToolbarContainer container = new ToolbarContainer();

		Matcher matcher = TOOLBAR_PATTERN.matcher(pageSource);

		if (!matcher.find()) throw new ParseException("Can't find toolbar");

		String toolbarSource = matcher.group();

		container.setSeriesContainer(new ToolbarSeriesParser().parse(toolbarSource));

		return container;
	}

}
