package tv.turbik.client;

import android.content.Context;
import de.greenrobot.dao.query.QueryBuilder;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import tv.turbik.client.episode.EpisodePage;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.season.SeasonPage;
import tv.turbik.client.season.SeasonPageEpisode;
import tv.turbik.client.series.SeriesPage;
import tv.turbik.client.series.SeriesPageSeries;
import tv.turbik.dao.*;

import java.util.List;

/**
 * @author Pavel Savinov
 * @version 18/08/14 21:31
 */
@EBean
public class SmartClient {

	@Bean TurboFilmClient rawClient;

	@RootContext Context context;

	private DaoMaster.DevOpenHelper helper;

	@AfterInject
	void afterInject() {
		helper = new DaoMaster.DevOpenHelper(context, "db", null);
	}

	public List<Series> getAllSeries(boolean forceUpdate) throws TurboException {

		DaoMaster readMaster = new DaoMaster(helper.getReadableDatabase());

		SeriesDao seriesDao = readMaster.newSession().getSeriesDao();

		List<Series> seriesList = seriesDao.loadAll();

		if (seriesList.isEmpty() || forceUpdate) {

			seriesDao = new DaoMaster(helper.getWritableDatabase()).newSession().getSeriesDao();

			seriesList.clear();
			seriesDao.deleteAll();

			SeriesPage page = rawClient.getSeriesPage();

			for (SeriesPageSeries pageSeries : page.getSeriesList()) {
				Series series = new Series();
				series.setId(pageSeries.getId());
				series.setAlias(pageSeries.getAlias());
				series.setNameEn(pageSeries.getNameEn());
				series.setNameRu(pageSeries.getNameRu());
				series.setSeasonsCount(pageSeries.getSeasonsCount());
				seriesList.add(series);
			}

			for (Series series : seriesList) {
				seriesDao.insert(series);
			}

		}

		return seriesList;
	}

	public Series getSeries(String alias) {
		SeriesDao seriesDao = new DaoMaster(helper.getReadableDatabase()).newSession().getSeriesDao();
		QueryBuilder<Series> builder = seriesDao.queryBuilder();
		builder.where(SeriesDao.Properties.Alias.eq(alias));
		return builder.build().unique();
	}

	public List<Episode> getEpisodes(String seriesAlias, byte season, boolean forceUpdate) throws TurboException {

		EpisodeDao episodeDao = new DaoMaster(helper.getReadableDatabase()).newSession().getEpisodeDao();

		QueryBuilder<Episode> builder = getEpisodeListQueryBuilder(seriesAlias, season, episodeDao);
		List<Episode> episodeList = builder.list();

		if (episodeList.isEmpty() || forceUpdate) {

			episodeDao = new DaoMaster(helper.getWritableDatabase()).newSession().getEpisodeDao();

			episodeList.clear();

			QueryBuilder<Episode> queryBuilder = getEpisodeListQueryBuilder(seriesAlias, season, episodeDao);
			queryBuilder.buildDelete().executeDeleteWithoutDetachingEntities();

			Series series = getSeries(seriesAlias);

			SeasonPage seasonPage = rawClient.getSeasonPage(series.getAlias(), season);

			for (SeasonPageEpisode seasonPageEpisode : seasonPage.getEpisodes()) {
				Episode episode = new Episode();
				episode.setSeriesAlias(seriesAlias);
				episode.setSeason(season);
				episode.setEpisode(seasonPageEpisode.getEpisode());
				episode.setNameEn(seasonPageEpisode.getNameEn());
				episode.setNameRu(seasonPageEpisode.getNameRu());
				episode.setSmallPosterUrl(seasonPageEpisode.getSmallPosterUrl());
				episodeList.add(episode);

				episodeDao.insert(episode);
			}

		}

		return episodeList;
	}

	public Episode getEpisode(String seriesAlias, byte season, byte episode, boolean forceUpdate) throws TurboException {

		EpisodeDao episodeDao = new DaoMaster(helper.getReadableDatabase()).newSession().getEpisodeDao();

		QueryBuilder<Episode> builder = getEpisodeQueryBuilder(seriesAlias, season, episode, episodeDao);

		Episode episodeItem = builder.unique();

		if (episodeItem == null || episodeItem.getHash() == null || forceUpdate) {

			episodeDao = new DaoMaster(helper.getWritableDatabase()).newSession().getEpisodeDao();

			builder = getEpisodeQueryBuilder(seriesAlias, season, episode, episodeDao);
			builder.buildDelete().executeDeleteWithoutDetachingEntities();

			episodeItem = new Episode();

			EpisodePage page = rawClient.getEpisodePage(seriesAlias, season, episode);

			episodeItem.setSeriesAlias(seriesAlias);
			episodeItem.setSeason(season);
			episodeItem.setEpisode(episode);
			episodeItem.setNameEn(page.getNameEn());
			episodeItem.setNameRu(page.getNameRu());
			episodeItem.setHash(page.getHash());
			episodeItem.setMetaData(page.getMetaData());
			episodeItem.setSmallPosterUrl(page.getSmallPosterUrl());
		}

		return episodeItem;
	}

	private QueryBuilder<Episode> getEpisodeQueryBuilder(String seriesAlias, byte season, byte episode, EpisodeDao episodeDao) {
		QueryBuilder<Episode> builder = getEpisodeListQueryBuilder(seriesAlias, season, episodeDao);
		builder.where(EpisodeDao.Properties.Episode.eq(episode));
		return builder;
	}

	private QueryBuilder<Episode> getEpisodeListQueryBuilder(String seriesAlias, byte season, EpisodeDao episodeDao) {
		QueryBuilder<Episode> builder = episodeDao.queryBuilder();
		builder.where(EpisodeDao.Properties.SeriesAlias.eq(seriesAlias));
		builder.where(EpisodeDao.Properties.Season.eq(season));
		return builder;
	}

}
