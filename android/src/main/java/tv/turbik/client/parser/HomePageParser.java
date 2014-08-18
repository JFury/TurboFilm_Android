package tv.turbik.client.parser;

import tv.turbik.client.container.HomePage;
import tv.turbik.client.exception.client.ParseException;

/**
 * @author Pavel Savinov
 * @version 17/08/14 00:01
 */
public class HomePageParser extends Parser {

	public HomePage parse(String text) throws ParseException {
		HomePage page = new HomePage();
		page.setTopSeries(new ToolbarSeriesParser().parse(text));
		return page;
	}

}
