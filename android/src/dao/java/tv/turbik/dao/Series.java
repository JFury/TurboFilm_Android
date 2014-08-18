package tv.turbik.dao;

import java.util.List;
import tv.turbik.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table SERIES.
 */
public class Series {

    private Long id;
    private String alias;
    private String nameEn;
    private String nameRu;
    private String description;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient SeriesDao myDao;

    private List<Episode> episodeList;

    public Series() {
    }

    public Series(Long id) {
        this.id = id;
    }

    public Series(Long id, String alias, String nameEn, String nameRu, String description) {
        this.id = id;
        this.alias = alias;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
        this.description = description;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSeriesDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Episode> getEpisodeList() {
        if (episodeList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EpisodeDao targetDao = daoSession.getEpisodeDao();
            List<Episode> episodeListNew = targetDao._querySeries_EpisodeList(id);
            synchronized (this) {
                if(episodeList == null) {
                    episodeList = episodeListNew;
                }
            }
        }
        return episodeList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetEpisodeList() {
        episodeList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
