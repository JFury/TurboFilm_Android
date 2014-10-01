package tv.turbik.client.episode;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import tv.turbik.client.Parser;
import tv.turbik.client.TurboFilmClient;
import tv.turbik.client.exception.client.ParseException;

import java.util.regex.Pattern;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 24.11.13 19:45
 */
public class Video extends Parser {

	private static char[] encoded = new char[]{'2', 'I', '0', '=', '3', 'Q', '8', 'V', '7', 'X', 'G', 'M', 'R', 'U', 'H', '4', '1', 'Z', '5', 'D', 'N', '6', 'L', '9', 'B', 'W'};
	private static char[] normal = new char[]{'x', 'u', 'Y', 'o', 'k', 'n', 'g', 'r', 'm', 'T', 'w', 'f', 'd', 'c', 'e', 's', 'i', 'l', 'y', 't', 'p', 'b', 'z', 'a', 'J', 'v'};

	private static final Pattern META_SCREEN = Pattern.compile("<screen>(.*?)</screen>");
	private static final Pattern META_DEFAULT_SOURCE = Pattern.compile("<sources2>.*?<default>(.*?)</default>.*?</sources2>", Pattern.DOTALL);
	private static final Pattern META_HQ_SOURCE = Pattern.compile("<sources2>.*?<hq>(.*?)</hq>.*?</sources2>", Pattern.DOTALL);
	private static final Pattern META_EID = Pattern.compile("<eid>(.*?)</eid>");
	private static final Pattern META_ASPECT = Pattern.compile("<aspect>(.*?)</aspect>");

	public static String decodeMeta(String source) {

		source = source.replace("%2b", "+");
		source = source.replace("%3d", "=");
		source = source.replace("%2f", "/");

		for (int i = 0; i < normal.length; i++) {
			source = source.replace(String.valueOf(normal[i]), "___");
			source = source.replace(encoded[i], normal[i]);
			source = source.replace("___", String.valueOf(encoded[i]));
		}

		return new String(Base64.decodeBase64(source.getBytes()));
	}

	public static String generateUrl(VideoMeta meta, String hash, String lang, boolean isHq, int time) {

		String p6 = DigestUtils.shaHex(hash + Math.random());

		return new StringBuilder("https://cdn." + TurboFilmClient.DOMAIN + "/")
				.append(DigestUtils.shaHex(lang)).append('/')
				.append(meta.getEid()).append('/')
				.append(isHq ? meta.getHqSource() : meta.getDefaultSource()).append('/')
				.append(time).append('/')
				.append(hash).append('/')
				.append(p6).append('/')
				.append(DigestUtils.shaHex(p6 + String.valueOf(meta.getEid()) + "A2DC51DE0F8BC1E9"))
				.toString();

	}

	public static VideoMeta parseMeta(String decodedMeta) throws ParseException {

		VideoMeta meta = new VideoMeta();

		meta.setScreen(parseString(decodedMeta, META_SCREEN));
		meta.setDefaultSource(parseString(decodedMeta, META_DEFAULT_SOURCE));
		meta.setHqSource(parseString(decodedMeta, META_HQ_SOURCE));

		meta.setEid(Integer.parseInt(parseString(decodedMeta, META_EID)));
		meta.setAspect(Integer.parseInt(parseString(decodedMeta, META_ASPECT)));

		return meta;
	}



}
