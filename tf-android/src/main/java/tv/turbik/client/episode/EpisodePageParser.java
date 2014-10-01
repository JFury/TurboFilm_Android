package tv.turbik.client.episode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.turbik.client.Parser;
import tv.turbik.client.exception.client.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 24.11.13 19:04
 */
public class EpisodePageParser extends Parser {

	private static final Logger L = LoggerFactory.getLogger(EpisodePageParser.class.getSimpleName());

	private static final Pattern NAME_EN = Pattern.compile("<a href=\"/Series/.*?\" class=\"en\">(.*?)</a>");
	private static final Pattern NAME_RU = Pattern.compile("<a href=\"/Series/.*?\" class=\"ru\">(.*?)</a>");

	private static final Pattern EPISODE_INFO = Pattern.compile("<span class=\"maine\" title=\"Дата выхода: (.*?)\">(.*?) / (.*?) <span class=\"se\">Эпизод: (.*?), Сезон: (.*?)</span></span>");

	private static final Pattern META_DATA = Pattern.compile("<input type=\"hidden\" id=\"metadata\" value=\"(.*?)\" />");
	private static final Pattern HASH = Pattern.compile("<input type=\"hidden\" id=\"hash\" value=\"(.*?)\" />");

	public EpisodePage parse(String pageSource) throws ParseException {

		EpisodePage page = new EpisodePage();
		String metaData = Video.decodeMeta(parseString(pageSource, META_DATA));
		L.trace("MetaData: " + metaData);
		page.setMetaData(metaData);
		page.setHash(new StringBuilder(parseString(pageSource, HASH)).reverse().toString());


		Matcher matcher = EPISODE_INFO.matcher(pageSource);

		if (matcher.find()) {

			String releaseDate = matcher.group(1);
			String nameEn = matcher.group(2);
			String nameRu = matcher.group(3);
			String episodeNumber = matcher.group(4);
			String seasonNumber = matcher.group(5);

			page.setNameEn(nameEn);
			page.setNameRu(nameRu);

		}

		return page;
	}

}
