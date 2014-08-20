package tv.turbik.screens.episode;

import android.net.Uri;
import android.widget.VideoView;
import com.actionbarsherlock.app.SherlockActivity;
import org.androidannotations.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.turbik.R;
import tv.turbik.client.SmartClient;
import tv.turbik.client.episode.Video;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.exception.client.ParseException;
import tv.turbik.client.exception.server.NotLoggedInException;
import tv.turbik.dao.Episode;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 13:08
 */
@EActivity(R.layout.episode)
public class EpisodeActivity extends SherlockActivity {

	private static final Logger L = LoggerFactory.getLogger(EpisodeActivity.class.getSimpleName());

	@ViewById VideoView video;

	@Extra String seriesAlias;
	@Extra byte season;
	@Extra byte episode;

	@Bean SmartClient client;

	@AfterViews
	void afterViews() {
		loadData();
	}

	@Background
	void loadData() {

		try {
			Episode episodeItem = client.getEpisode(seriesAlias, season, episode, false);
			update(episodeItem);

		} catch (NotLoggedInException e) {
			e.printStackTrace();

		} catch (TurboException e) {
			e.printStackTrace();
		}

	}

	@UiThread
	void update(Episode episode) {

		String hash = episode.getHash();
		String url = null;
		try {
			url = Video.generateUrl(Video.parseMeta(episode.getMetaData()), hash, "ru", false, 0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		L.trace("URL: " + url);
		video.setVideoURI(Uri.parse(url));
		video.start();

	}

}
