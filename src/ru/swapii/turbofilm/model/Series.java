package ru.swapii.turbofilm.model;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 05.05.12
 * Time: 0:44
 */
public class Series {

	private int id;

	/**
	 * Part of URL
	 */
	private String path;

	private String nameRu;

	private String nameEn;
	private int seasonsCount;
	private int episodesCount;
	private String description;

	public void setPath(String path) {
		this.path = path;
	}

	public String getNameRu() {
		return nameRu;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setSeasonsCount(int seasonsCount) {
		this.seasonsCount = seasonsCount;
	}

	public void setEpisodesCount(int episodesCount) {
		this.episodesCount = episodesCount;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getSeasonsCount() {
		return seasonsCount;
	}

	public String getPath() {
		return path;
	}

	public String getMediumLogoUrl() {
		return "http://s.turbofilm.tv/i/series/" + id + "s.jpg";
	}

	public int getEpisodesCount() {
		return episodesCount;
	}

	public String getNameEn() {
		return nameEn;
	}

}
