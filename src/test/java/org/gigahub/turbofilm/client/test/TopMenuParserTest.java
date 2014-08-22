package org.gigahub.turbofilm.client.test;

import org.apache.commons.io.IOUtils;
import org.gigahub.turbofilm.client.ParseException;
import org.gigahub.turbofilm.client.container.ToolbarSeriesContainer;
import org.gigahub.turbofilm.client.parser.ToolbarSeriesParser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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
