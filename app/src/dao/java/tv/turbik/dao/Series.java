package tv.turbik.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table SERIES.
 */
public class Series {

    private Short id;
    /** Not-null value. */
    private String alias;
    /** Not-null value. */
    private String nameEn;
    private String nameRu;
    private String description;
    private Byte seasonsCount;

    public Series() {
    }

    public Series(Short id, String alias, String nameEn, String nameRu, String description, Byte seasonsCount) {
        this.id = id;
        this.alias = alias;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
        this.description = description;
        this.seasonsCount = seasonsCount;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getAlias() {
        return alias;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /** Not-null value. */
    public String getNameEn() {
        return nameEn;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getSeasonsCount() {
        return seasonsCount;
    }

    public void setSeasonsCount(Byte seasonsCount) {
        this.seasonsCount = seasonsCount;
    }

}