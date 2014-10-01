package tv.turbik.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import tv.turbik.dao.Series;
import tv.turbik.dao.Episode;
import tv.turbik.dao.Video;
import tv.turbik.dao.VideoPart;

import tv.turbik.dao.SeriesDao;
import tv.turbik.dao.EpisodeDao;
import tv.turbik.dao.VideoDao;
import tv.turbik.dao.VideoPartDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig seriesDaoConfig;
    private final DaoConfig episodeDaoConfig;
    private final DaoConfig videoDaoConfig;
    private final DaoConfig videoPartDaoConfig;

    private final SeriesDao seriesDao;
    private final EpisodeDao episodeDao;
    private final VideoDao videoDao;
    private final VideoPartDao videoPartDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        seriesDaoConfig = daoConfigMap.get(SeriesDao.class).clone();
        seriesDaoConfig.initIdentityScope(type);

        episodeDaoConfig = daoConfigMap.get(EpisodeDao.class).clone();
        episodeDaoConfig.initIdentityScope(type);

        videoDaoConfig = daoConfigMap.get(VideoDao.class).clone();
        videoDaoConfig.initIdentityScope(type);

        videoPartDaoConfig = daoConfigMap.get(VideoPartDao.class).clone();
        videoPartDaoConfig.initIdentityScope(type);

        seriesDao = new SeriesDao(seriesDaoConfig, this);
        episodeDao = new EpisodeDao(episodeDaoConfig, this);
        videoDao = new VideoDao(videoDaoConfig, this);
        videoPartDao = new VideoPartDao(videoPartDaoConfig, this);

        registerDao(Series.class, seriesDao);
        registerDao(Episode.class, episodeDao);
        registerDao(Video.class, videoDao);
        registerDao(VideoPart.class, videoPartDao);
    }
    
    public void clear() {
        seriesDaoConfig.getIdentityScope().clear();
        episodeDaoConfig.getIdentityScope().clear();
        videoDaoConfig.getIdentityScope().clear();
        videoPartDaoConfig.getIdentityScope().clear();
    }

    public SeriesDao getSeriesDao() {
        return seriesDao;
    }

    public EpisodeDao getEpisodeDao() {
        return episodeDao;
    }

    public VideoDao getVideoDao() {
        return videoDao;
    }

    public VideoPartDao getVideoPartDao() {
        return videoPartDao;
    }

}