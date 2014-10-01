package tv.turbik.client.home;

import tv.turbik.client.Parser;
import tv.turbik.client.exception.client.ParseException;
import tv.turbik.client.toolbar.ToolbarParser;

/**
 * @author Pavel Savinov
 * @version 17/08/14 00:01
 */
public class HomePageParser extends Parser {

	public HomePage parse(String pageSource) throws ParseException {
		HomePage page = new HomePage();
		page.setToolbarContainer(new ToolbarParser().parse(pageSource));
		return page;
	}

}
