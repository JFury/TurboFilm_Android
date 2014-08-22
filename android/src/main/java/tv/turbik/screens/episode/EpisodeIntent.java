package org.gigahub.turbofilm.screens.episode;

import android.content.Context;
import android.content.Intent;
import org.gigahub.turbofilm.client.model.BasicEpisode;
import org.gigahub.turbofilm.client.model.BasicSeries;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 13:08
 */
public class EpisodeIntent extends Intent {

	public static final String ALIAS = "alias";
	public static final String SERIES_NAME_EN = "seriesNameEn";
	public static final String SERIES_NAME_RU = "seriesNameRu";
	public static final String SEASON = "season";
	public static final String EPISODE = "episode";
	public static final String EPISODE_NAME_EN = "episodeNameEn";
	public static final String EPISODE_NAME_RU = "episodeNameRu";

	public EpisodeIntent(Context context, BasicSeries series, BasicEpisode episode) {
		super(context, EpisodeActivity.class);

		putExtra(ALIAS, series.getAlias());
		putExtra(SERIES_NAME_EN, series.getNameEn());
		putExtra(SERIES_NAME_RU, series.getNameRu());
		putExtra(SEASON, episode.getSeason());
		putExtra(EPISODE, episode.getEpisode());
		putExtra(EPISODE_NAME_EN, episode.getNameEn());
		putExtra(EPISODE_NAME_RU, episode.getNameRu());
	}

}
