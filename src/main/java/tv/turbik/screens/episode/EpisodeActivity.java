package tv.turbik.screens.episode;

import android.net.Uri;
import android.widget.VideoView;
import com.actionbarsherlock.app.SherlockActivity;
import org.androidannotations.annotations.*;
import tv.turbik.R;
import tv.turbik.client.NotLoggedInException;
import tv.turbik.client.ParseException;
import tv.turbik.client.TurboFilmClient;
import tv.turbik.client.Video;
import tv.turbik.client.container.EpisodePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 13:08
 */
@EActivity(R.layout.episode)
public class EpisodeActivity extends SherlockActivity {

	private static final Logger L = LoggerFactory.getLogger(EpisodeActivity.class.getSimpleName());

	@ViewById VideoView video;

	@Extra String alias;
	@Extra int season;
	@Extra int episode;

	@Bean TurboFilmClient client;

	@AfterViews
	void afterViews() {
		loadData();
	}

	@Background
	void loadData() {

		try {
			EpisodePage page = client.getEpisode(alias, season, episode);
			update(page);

		} catch (NotLoggedInException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (ParseException e) {
			e.printStackTrace();

		}

	}

	@UiThread
	void update(EpisodePage page) {

		String hash = page.getHash();
		String url = Video.generateUrl(page.getMetaData(), hash, "ru", false, 0);
		L.trace("URL: " + url);
		video.setVideoURI(Uri.parse(url));
		video.start();

	}

}
