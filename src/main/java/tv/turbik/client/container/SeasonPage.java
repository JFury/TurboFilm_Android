package tv.turbik.client.container;

import tv.turbik.client.model.BasicEpisode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 13:28
 */
public class SeasonPage {

	private int seasonsCount;
	private int currentSeason;
	private List<BasicEpisode> episodes = new ArrayList<BasicEpisode>();
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

	public void setSeasonsCount(int seasonsCount) {
		this.seasonsCount = seasonsCount;
	}

	public int getCurrentSeason() {
		return currentSeason;
	}

	public void setCurrentSeason(int currentSeason) {
		this.currentSeason = currentSeason;
	}

	public String getSeriesDescription() {
		return seriesDescription;
	}

	public void setSeriesDescription(String seriesDescription) {
		this.seriesDescription = seriesDescription;
	}

	public List<BasicEpisode> getEpisodes() {
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
