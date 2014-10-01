package tv.turbik.test.client;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import tv.turbik.client.exception.client.ParseException;
import tv.turbik.client.home.HomePage;
import tv.turbik.client.home.HomePageParser;
import tv.turbik.client.toolbar.series.ToolbarSeriesContainer;
import tv.turbik.client.toolbar.series.ToolbarSeriesParser;

import java.io.IOException;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 11:10
 */
public class TopMenuParserTest {

	private static HomePage container;

	@BeforeClass
	public static void parse() throws IOException, ParseException {
		String text = IOUtils.toString(TopMenuParserTest.class.getResourceAsStream("/pages/home.htm"));
		container = new HomePageParser().parse(text);
	}

	@Test
	public void parseComplete() {
		Assert.assertNotNull(container);
		Assert.assertFalse(container.getToolbarContainer().getSeriesContainer().getMySeries().isEmpty());
		Assert.assertFalse(container.getToolbarContainer().getSeriesContainer().getOtherSeries().isEmpty());
	}

}
