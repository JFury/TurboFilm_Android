package tv.turbik.test.client;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import tv.turbik.client.exception.client.ParseException;
import tv.turbik.client.toolbar.series.ToolbarSeriesContainer;
import tv.turbik.client.toolbar.series.ToolbarSeriesParser;

import java.io.IOException;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 11:10
 */
public class TopMenuParserTest {

	private static ToolbarSeriesContainer container;

	@BeforeClass
	public static void parse() throws IOException, ParseException {
		String text = IOUtils.toString(TopMenuParserTest.class.getResourceAsStream("/pages/home.htm"));
		container = new ToolbarSeriesParser().parse(text);
	}

	@Test
	public void parseComplete() {
		Assert.assertNotNull(container);
		Assert.assertFalse(container.getMySeries().isEmpty());
		Assert.assertFalse(container.getOtherSeries().isEmpty());
	}

}
