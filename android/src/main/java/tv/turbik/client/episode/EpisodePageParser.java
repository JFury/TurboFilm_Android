package tv.turbik.client.episode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.turbik.client.Parser;
import tv.turbik.client.exception.client.ParseException;

import java.util.regex.Pattern;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 24.11.13 19:04
 */
public class EpisodePageParser extends Parser {

	private static final Logger L = LoggerFactory.getLogger(EpisodePageParser.class.getSimpleName());

	private static final Pattern NAME_EN = Pattern.compile("<a href=\"/Series/.*?\" class=\"en\">(.*?)</a>");
	private static final Pattern NAME_RU = Pattern.compile("<a href=\"/Series/.*?\" class=\"ru\">(.*?)</a>");

	private static final Pattern META_DATA = Pattern.compile("<input type=\"hidden\" id=\"metadata\" value=\"(.*?)\" />");
	private static final Pattern HASH = Pattern.compile("<input type=\"hidden\" id=\"hash\" value=\"(.*?)\" />");

	public EpisodePage parse(String pageSource) throws ParseException {

		EpisodePage page = new EpisodePage();
		String metaData = Video.decodeMeta(parseString(pageSource, META_DATA));
		L.trace("MetaData: " + metaData);
		page.setMetaData(metaData);
		page.setHash(new StringBuilder(parseString(pageSource, HASH)).reverse().toString());
		page.setNameEn(parseString(pageSource, NAME_EN));
		page.setNameRu(parseString(pageSource, NAME_RU));
		//page.setSmallPosterUrl();

		return page;
	}

}
