package tv.turbik.client.series;

import tv.turbik.client.toolbar.series.ToolbarSeries;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Pavel Savinov
 * @version 19/08/14 00:10
 */
public class SeriesPageSeries extends ToolbarSeries {

	private byte seasonsCount;

	private short episodesCount;

	private String shortDescription;

	/**
	 * Длительность одной серии (в минутах)
	 */
	private int episodeDuration;

	/**
	 * Дата премьеры сериала
	 */
	private String premierDate;

	private Set<String> genres = new TreeSet<String>();

	public byte getSeasonsCount() {
		return seasonsCount;
	}

	public void setSeasonsCount(byte seasonsCount) {
		this.seasonsCount = seasonsCount;
	}

	public short getEpisodesCount() {
		return episodesCount;
	}

	public void setEpisodesCount(short episodesCount) {
		this.episodesCount = episodesCount;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public int getEpisodeDuration() {
		return episodeDuration;
	}

	public void setEpisodeDuration(int episodeDuration) {
		this.episodeDuration = episodeDuration;
	}

	public String getPremierDate() {
		return premierDate;
	}

	public void setPremierDate(String premierDate) {
		this.premierDate = premierDate;
	}

	public Set<String> getGenres() {
		return genres;
	}

	public void setGenres(Set<String> genres) {
		this.genres = genres;
	}

}
