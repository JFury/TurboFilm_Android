package tv.turbik.events;

import tv.turbik.dao.Episode;

/**
 * Created by pavel on 27/11/14.
 */
public class EpisodeSelectedEvent {

	private final Episode episode;

	public EpisodeSelectedEvent(Episode episode) {
		this.episode = episode;
	}

	public Episode getEpisode() {
		return episode;
	}
}
