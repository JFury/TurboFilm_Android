package org.gigahub.turbofilm.screens.episode;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;
import com.actionbarsherlock.app.SherlockActivity;
import org.androidannotations.annotations.*;
import org.gigahub.turbofilm.R;
import org.gigahub.turbofilm.client.NotLoggedInException;
import org.gigahub.turbofilm.client.ParseException;
import org.gigahub.turbofilm.client.TurboFilmClient;
import org.gigahub.turbofilm.client.Video;
import org.gigahub.turbofilm.client.container.EpisodePage;
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
	@ViewById View loading;

	@FragmentById VideoControls controls;

	@Extra String alias;
	@Extra int season;
	@Extra int episode;

	@Bean TurboFilmClient client;

	@AfterViews
	void afterViews() {

		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.semi_transparent));

		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

		controls.setVideo(video);

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
	void update(EpisodePage page) {

		String hash = page.getHash();
		String url = Video.generateUrl(page.getMetaData(), hash, "ru", false, 0);
		L.trace("URL: " + url);
		video.setVideoURI(Uri.parse(url));

		video.requestLayout();
	}

}
