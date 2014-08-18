package tv.turbik.screens.episode;

import android.net.Uri;
import android.widget.VideoView;
import com.actionbarsherlock.app.SherlockActivity;
import org.androidannotations.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.turbik.R;
import tv.turbik.client.TurbikClient;
import tv.turbik.client.Video;
import tv.turbik.client.container.EpisodePage;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.exception.server.NotLoggedInException;

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

	@Bean TurbikClient client;

	@AfterViews
	void afterViews() {
		loadData();
	}

	@Background
	void loadData() {

		try {
			EpisodePage page = client.getEpisodePage(alias, season, episode);
			update(page);

		} catch (NotLoggedInException e) {
			e.printStackTrace();

		} catch (TurboException e) {
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
