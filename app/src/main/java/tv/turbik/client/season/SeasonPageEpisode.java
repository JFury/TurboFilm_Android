package tv.turbik.client.season;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 13:11
 */
public class SeasonPageEpisode {

	private String smallPosterUrl;
	private byte season;
	private byte episode;
	private String nameEn;
	private String nameRu;
	private boolean hasEnAudio;
	private boolean hasRuAudio;
	private boolean hasEnSubs;
	private boolean hasRuSubs;
	private boolean hasHQ;

	public String getSmallPosterUrl() {
		return smallPosterUrl;
	}

	public void setSmallPosterUrl(String smallPosterUrl) {
		this.smallPosterUrl = smallPosterUrl;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(byte season) {
		this.season = season;
	}

	public byte getEpisode() {
		return episode;
	}

	public void setEpisode(byte episode) {
		this.episode = episode;
	}

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

	public boolean isHasEnAudio() {
		return hasEnAudio;
	}

	public void setHasEnAudio(boolean hasEnAudio) {
		this.hasEnAudio = hasEnAudio;
	}

	public boolean isHasRuAudio() {
		return hasRuAudio;
	}

	public void setHasRuAudio(boolean hasRuAudio) {
		this.hasRuAudio = hasRuAudio;
	}

	public boolean isHasEnSubs() {
		return hasEnSubs;
	}

	public void setHasEnSubs(boolean hasEnSubs) {
		this.hasEnSubs = hasEnSubs;
	}

	public boolean isHasRuSubs() {
		return hasRuSubs;
	}

	public void setHasRuSubs(boolean hasRuSubs) {
		this.hasRuSubs = hasRuSubs;
	}

	public boolean isHasHQ() {
		return hasHQ;
	}

	public void setHasHQ(boolean hasHQ) {
		this.hasHQ = hasHQ;
	}

}
