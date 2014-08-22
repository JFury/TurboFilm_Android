package tv.turbik.client.episode;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 13:38
 */
public class EpisodePage {

	private String nameEn;
	private String nameRu;

	private String hash;
	private String metaData;

	private String smallPosterUrl;

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameRu() {
		return nameRu;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public String getSmallPosterUrl() {
		return smallPosterUrl;
	}

	public void setSmallPosterUrl(String smallPosterUrl) {
		this.smallPosterUrl = smallPosterUrl;
	}

}
