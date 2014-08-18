package tv.turbik.client.parser;

import tv.turbik.client.exception.client.ParseException;
import tv.turbik.client.Video;
import tv.turbik.client.container.EpisodePage;
import tv.turbik.client.model.VideoMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 24.11.13 19:04
 */
public class EpisodePageParser extends Parser {

	private static final Logger L = LoggerFactory.getLogger(EpisodePageParser.class.getSimpleName());

	private static final Pattern META_DATA = Pattern.compile("<input type=\"hidden\" id=\"metadata\" value=\"(.*?)\" />");
	private static final Pattern HASH = Pattern.compile("<input type=\"hidden\" id=\"hash\" value=\"(.*?)\" />");

	private static final Pattern META_SCREEN = Pattern.compile("<screen>(.*?)</screen>");
	private static final Pattern META_DEFAULT_SOURCE = Pattern.compile("<sources2>.*?<default>(.*?)</default>.*?</sources2>", Pattern.DOTALL);
	private static final Pattern META_HQ_SOURCE = Pattern.compile("<sources2>.*?<hq>(.*?)</hq>.*?</sources2>", Pattern.DOTALL);
	private static final Pattern META_EID = Pattern.compile("<eid>(.*?)</eid>");
	private static final Pattern META_ASPECT = Pattern.compile("<aspect>(.*?)</aspect>");

	public EpisodePage parse(String text) throws ParseException {

		EpisodePage page = new EpisodePage();

		Matcher metaMatcher = META_DATA.matcher(text);
		if (!metaMatcher.find()) throw new ParseException("Can't parse episode page");
		page.setMetaData(parseMeta(metaMatcher.group(1)));

		Matcher hashMatcher = HASH.matcher(text);
		if (!hashMatcher.find()) throw new ParseException("Can't parse episode page");
		page.setHash(new StringBuilder(hashMatcher.group(1)).reverse().toString());

		return page;
	}

	private VideoMeta parseMeta(String encodedMeta) throws ParseException {

		String decodedMeta = Video.decodeMeta(encodedMeta);

		VideoMeta meta = new VideoMeta();

		meta.setScreen(getString(decodedMeta, META_SCREEN));
		meta.setDefaultSource(getString(decodedMeta, META_DEFAULT_SOURCE));
		meta.setHqSource(getString(decodedMeta, META_HQ_SOURCE));

		meta.setEid(Integer.parseInt(getString(decodedMeta, META_EID)));
		meta.setAspect(Integer.parseInt(getString(decodedMeta, META_ASPECT)));

		return meta;
	}

}
