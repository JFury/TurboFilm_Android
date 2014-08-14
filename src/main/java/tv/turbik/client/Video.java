package tv.turbik.client;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import tv.turbik.client.model.VideoMeta;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 24.11.13 19:45
 */
public class Video {

	private static char[] encoded = new char[]{'2','I','0','=','3','Q','8','V','7','X','G','M','R','U','H','4','1','Z','5','D','N','6','L','9','B','W'};
	private static char[] normal = new char[]{'x','u','Y','o','k','n','g','r','m','T','w','f','d','c','e','s','i','l','y','t','p','b','z','a','J','v'};

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

}
