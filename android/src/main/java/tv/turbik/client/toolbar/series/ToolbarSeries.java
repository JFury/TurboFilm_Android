package org.gigahub.turbofilm.client.model;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 22.11.13 13:34
 */
public class BasicSeries {

	private int id;
	private String alias;
	private String nameEn;
	private String nameRu;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

}
