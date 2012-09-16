package ru.swapii.turbofilm.model;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 05.08.12
 * Time: 19:55
 */
public class Episode {

	private String path;
	private String coverSmallPath;

	private String nameEn;
	private String nameRu;

	private int season;
	private int episode;

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public void setEpisode(int episode) {
		this.episode = episode;
	}

	public void setCoverSmallPath(String coverSmallPath) {
		this.coverSmallPath = coverSmallPath;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCoverSmallPath() {
		return coverSmallPath;
	}

	public String getNameEn() {
		return nameEn;
	}

	public String getNameRu() {
		return nameRu;
	}

	public int getSeason() {
		return season;
	}

	public int getEpisode() {
		return episode;
	}
}
