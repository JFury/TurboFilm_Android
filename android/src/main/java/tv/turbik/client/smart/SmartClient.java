package tv.turbik.client.smart;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import tv.turbik.client.TurbikClient;
import tv.turbik.client.container.HomePage;
import tv.turbik.client.container.SeasonPage;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.model.BasicEpisode;
import tv.turbik.client.model.BasicSeries;
import tv.turbik.dao.Episode;
import tv.turbik.dao.Series;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov
 * @version 18/08/14 21:31
 */
@EBean
public class SmartClient {

	@Bean TurbikClient turbikClient;

	public List<Series> getAllSeries() throws TurboException{

		HomePage homePage = turbikClient.getHomePage();

		List<Series> seriesList = new ArrayList<Series>();

		for (BasicSeries basicSeries : homePage.getTopSeries().getMySeries()) {
			Series series = new Series();
			series.setId((long) basicSeries.getId());
			series.setAlias(basicSeries.getAlias());
			series.setNameEn(basicSeries.getNameEn());
			series.setNameRu(basicSeries.getNameRu());
			seriesList.add(series);
		}

		for (BasicSeries basicSeries : homePage.getTopSeries().getMySeries()) {
			Series series = new Series();
			series.setId((long) basicSeries.getId());
			series.setAlias(basicSeries.getAlias());
			series.setNameEn(basicSeries.getNameEn());
			series.setNameRu(basicSeries.getNameRu());
			seriesList.add(series);
		}

		return seriesList;
	}

	public Series getSeries(long id) {
		return null;
	}

	public List<Episode> getEpisodes(long seriesId, int season) throws TurboException {

		Series series = getSeries(seriesId);

		SeasonPage seasonPage = turbikClient.getSeasonPage(series.getAlias(), season);

		ArrayList<Episode> episodes = new ArrayList<Episode>();
		for (BasicEpisode basicEpisode : seasonPage.getEpisodes()) {
			Episode episode = new Episode();
			episode.setSeriesId(seriesId);
			episode.setSeason((byte) season);
			episode.setEpisode((byte) basicEpisode.getEpisode());
			episode.setNameEn(basicEpisode.getNameEn());
			episode.setNameRu(basicEpisode.getNameRu());
			episode.setSmallPosterUrl(basicEpisode.getSmallPosterUrl());
			episodes.add(episode);
		}

		return episodes;
	}

}
