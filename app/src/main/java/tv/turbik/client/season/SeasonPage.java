package tv.turbik.client.season;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 13:28
 */
public class SeasonPage {

	private byte seasonsCount;
	private byte currentSeason;
	private List<SeasonPageEpisode> episodes = new ArrayList<SeasonPageEpisode>();
	private List<String> translations = new ArrayList<String>();

	/**
	 * Описание сериала
	 */
	private String seriesDescription;
	private String seriesNameEn;
	private String seriesNameRu;

	public int getSeasonsCount() {
		return seasonsCount;
	}

	public void setSeasonsCount(byte seasonsCount) {
		this.seasonsCount = seasonsCount;
	}

	public byte getCurrentSeason() {
		return currentSeason;
	}

	public void setCurrentSeason(byte currentSeason) {
		this.currentSeason = currentSeason;
	}

	public String getSeriesDescription() {
		return seriesDescription;
	}

	public void setSeriesDescription(String seriesDescription) {
		this.seriesDescription = seriesDescription;
	}

	public List<SeasonPageEpisode> getEpisodes() {
		return episodes;
	}

	public List<String> getTranslations() {
		return translations;
	}

	public void setSeriesNameEn(String seriesNameEn) {
		this.seriesNameEn = seriesNameEn;
	}

	public String getSeriesNameEn() {
		return seriesNameEn;
	}

	public void setSeriesNameRu(String seriesNameRu) {
		this.seriesNameRu = seriesNameRu;
	}

	public String getSeriesNameRu() {
		return seriesNameRu;
	}
}
