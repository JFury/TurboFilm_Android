package tv.turbik.client.container;

import tv.turbik.client.model.VideoMeta;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 13:38
 */
public class EpisodePage {

	private String hash;
	private VideoMeta metaData;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public VideoMeta getMetaData() {
		return metaData;
	}

	public void setMetaData(VideoMeta metaData) {
		this.metaData = metaData;
	}

}
