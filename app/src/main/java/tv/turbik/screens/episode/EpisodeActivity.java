package tv.turbik.screens.episode;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;
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
public class EpisodeActivity extends Activity {

	private static final Logger L = LoggerFactory.getLogger(EpisodeActivity.class.getSimpleName());

	private TextView nameEn;
	private TextView nameRu;
	private TextView seasonEpisodeNumber;

	@ViewById VideoView video;
	@ViewById View loading;

	@FragmentById VideoControls controls;

	@Extra String seriesAlias;
	@Extra byte season;
	@Extra byte episode;

	@Bean SmartClient client;

	@AfterViews
	void afterViews() {

		/*ActionBar actionBar = getSupportActionBar();

		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.semi_transparent));
		actionBar.setCustomView(R.layout.episode_header);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);*/

		/*View view = actionBar.getCustomView();
		nameEn = (TextView) view.findViewById(R.id.name_en);
		nameRu = (TextView) view.findViewById(R.id.name_ru);
		seasonEpisodeNumber = (TextView) view.findViewById(R.id.season_episode_number);*/

		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

		controls.setVideo(video);

		/*seasonEpisodeNumber.setText(String.format("%d.%02d", season, episode));*/

		loadData();
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

	@Background
	void loadData() {

		try {

			Episode episodeItem = client.getEpisode(seriesAlias, season, episode, true);
			update(episodeItem);

		} catch (NotLoggedInException e) {
			e.printStackTrace();

		} catch (TurboException e) {
			e.printStackTrace();
		}
	}

	@UiThread
	void update(Episode episode) {

		nameEn.setText(episode.getNameEn());
		nameRu.setText(episode.getNameRu());

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
