package org.gigahub.turbofilm.client.test;

import org.apache.commons.io.IOUtils;
import org.gigahub.turbofilm.client.ParseException;
import org.gigahub.turbofilm.client.container.SeasonPage;
import org.gigahub.turbofilm.client.container.ToolbarSeriesContainer;
import org.gigahub.turbofilm.client.parser.SeasonPageParser;
import org.gigahub.turbofilm.client.parser.ToolbarSeriesParser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 11:10
 */
public class SeasonPageParserTest {

	private static SeasonPage container;

	@BeforeClass
	public static void parse() throws IOException, ParseException {
		String text = IOUtils.toString(SeasonPageParserTest.class.getResourceAsStream("/pages/Season1.htm"));
		container = new SeasonPageParser().parse(text);
	}

	@Test
	public void parseComplete() {
		Assert.assertNotNull(container);
		Assert.assertTrue(container.getSeasonsCount() > 0);
		Assert.assertTrue(container.getCurrentSeason() > 0);
		Assert.assertFalse(container.getEpisodes().isEmpty());
	}

}
