package org.gigahub.turbofilm.screens.episode;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.VideoView;
import org.gigahub.turbofilm.R;
import org.gigahub.turbofilm.client.NotLoggedInException;
import org.gigahub.turbofilm.client.ParseException;
import org.gigahub.turbofilm.client.TurboFilmClient;
import org.gigahub.turbofilm.client.Video;
import org.gigahub.turbofilm.client.container.EpisodePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 13:08
 */
@ContentView(R.layout.episode)
public class EpisodeActivity extends RoboActivity {

	private static final Logger L = LoggerFactory.getLogger(EpisodeActivity.class.getSimpleName());

	@InjectView(R.id.video) VideoView video;

	@InjectExtra(EpisodeIntent.ALIAS) String alias;
	@InjectExtra(EpisodeIntent.SEASON) int season;
	@InjectExtra(EpisodeIntent.EPISODE) int episode;

	@Inject TurboFilmClient client;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadData();
	}

	private void loadData() {
		new AsyncTask<Void, Void, EpisodePage>(){

			@Override
			protected EpisodePage doInBackground(Void... params) {

				try {
					return client.getEpisode(alias, season, episode);

				} catch (NotLoggedInException e) {
					e.printStackTrace();

				} catch (IOException e) {
					e.printStackTrace();

				} catch (ParseException e) {
					e.printStackTrace();

				}

				return null;
			}

			@Override
			protected void onPostExecute(EpisodePage episodePage) {

				String hash = episodePage.getHash();
				String url = Video.generateUrl(episodePage.getMetaData(), hash, "ru", false, 0);
				L.trace("URL: " + url);
				video.setVideoURI(Uri.parse(url));
				video.start();
			}

		}.execute();
	}

}