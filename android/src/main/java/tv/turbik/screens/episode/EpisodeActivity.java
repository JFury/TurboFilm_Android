package tv.turbik.screens.episode;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
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
	@ViewById View loading;

	@FragmentById VideoControls controls;

	@Extra String seriesAlias;
	@Extra byte season;
	@Extra byte episode;

	@Bean SmartClient client;

	@AfterViews
	void afterViews() {

		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.semi_transparent));

		//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

		controls.setVideo(video);

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

	public void videoPrepared(int width, int height) {

		loading.setVisibility(View.GONE);

		View root = findViewById(android.R.id.content);

		double rootAspect = (double) root.getWidth() / root.getHeight();
		double videoAspect = (double) width / height;

		ViewGroup.LayoutParams params = video.getLayoutParams();

		if (rootAspect < videoAspect) {
			params.width = (int) (root.getHeight() * videoAspect);
			params.height = root.getHeight();
		} else {
			params.width = root.getWidth();
			params.height = (int) (root.getWidth() / videoAspect);
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

		video.requestLayout();
	}

}
