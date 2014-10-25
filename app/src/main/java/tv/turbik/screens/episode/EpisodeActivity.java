package tv.turbik.screens.episode;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import org.androidannotations.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tv.turbik.R;
import tv.turbik.Utils;
import tv.turbik.client.SmartClient;
import tv.turbik.client.episode.Video;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.exception.client.ParseException;
import tv.turbik.client.exception.server.NotLoggedInException;
import tv.turbik.dao.Episode;
import tv.turbik.dao.Series;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 13:08
 */
@Fullscreen
@EActivity(R.layout.episode)
public class EpisodeActivity extends Activity {

	private static final Logger L = LoggerFactory.getLogger(EpisodeActivity.class.getSimpleName());

	@ViewById View header;
	@ViewById TextView seriesNameEnText;
	@ViewById TextView seriesNameRuText;
	@ViewById TextView seasonEpisodeText;
	@ViewById TextView episodeNameEnText;
	@ViewById TextView episodeNameRuText;

	@ViewById VideoView video;

	@ViewById View progressBar;

	@ViewById View footer;
	@ViewById ImageButton playButton;
	@ViewById TextView pastTime;
	@ViewById SeekBar seekBar;
	@ViewById TextView remainTime;

	@Extra String seriesAlias;
	@Extra byte season;
	@Extra byte episode;

	@Bean SmartClient client;

	private Drawable playIcon;
	private Drawable pauseIcon;
	private ScheduledExecutorService executor;

	private boolean isSeeking;
	private boolean isPlaying;
	private boolean isRemainShow;

	@AfterViews
	void afterViews() {

		playIcon = getResources().getDrawable(R.drawable.ic_action_play);
		pauseIcon = getResources().getDrawable(R.drawable.ic_action_pause);

		setSystemUiVisibilityVisible(true);

		pastTime.setText(Utils.convertTime(0));
		remainTime.setText("-" + Utils.convertTime(0));

		seasonEpisodeText.setText(String.format("%d.%02d", season, episode));

		video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				videoPrepared(mp.getVideoWidth(), mp.getVideoHeight());
			}
		});

		getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				boolean uiVisible = isUiVisible(visibility);
				header.setVisibility(uiVisible ? View.VISIBLE : View.INVISIBLE);
				footer.setVisibility(uiVisible ? View.VISIBLE : View.INVISIBLE);
			}
		});

		loadEpisode();
	}

	@Override
	protected void onResume() {
		super.onResume();
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				if (video != null && !isSeeking) {
					updateControls(video.getCurrentPosition());
				}
			}
		}, 0, 250, TimeUnit.MILLISECONDS);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (executor != null) {
			executor.shutdown();
			executor = null;
		}
	}

	@Click
	void backButton() {
		onBackPressed();
	}

	@Click
	void centerPanel() {
		switchControlsVisibility();
	}

	@Click
	void video() {
		switchControlsVisibility();
	}

	@Click
	void playButton() {
		if (video != null) {

			isPlaying = !isPlaying;

			if (isPlaying) {
				video.start();
			} else {
				video.pause();
			}

			playButton.setImageDrawable(isPlaying ? pauseIcon : playIcon);
		}
	}

	@UiThread
	void updateControls(int videoPosition) {
		int duration = video.getDuration();

		seekBar.setMax(duration);
		seekBar.setProgress(videoPosition);

		pastTime.setText(Utils.convertTime(videoPosition));

		if (duration > 0) {

			if (isRemainShow)
				remainTime.setText(Utils.convertTime(videoPosition - duration));
			else
				remainTime.setText(Utils.convertTime(duration));
		}
	}

	@Click
	void remainTime() {
		isRemainShow = !isRemainShow;
	}

	@SeekBarTouchStart(R.id.seekBar)
	void seekStart() {
		isSeeking = true;
	}

	@SeekBarTouchStop(R.id.seekBar)
	void seekStop() {
		if (video != null) video.seekTo(seekBar.getProgress());
		isSeeking = false;
	}

	public void videoPrepared(int width, int height) {

		progressBar.setVisibility(View.GONE);

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
	void loadEpisode() {

		try {

			Series series = client.getSeries(seriesAlias);
			seriesLoaded(series);

			Episode episodeItem = client.getEpisode(seriesAlias, season, episode, true);
			episodeLoaded(episodeItem);

		} catch (NotLoggedInException e) {
			e.printStackTrace();

		} catch (TurboException e) {
			e.printStackTrace();
		}
	}

	@UiThread
	void seriesLoaded(Series series) {
		seriesNameEnText.setText(series.getNameEn());
		seriesNameRuText.setText(series.getNameRu());
	}

	@UiThread
	void episodeLoaded(Episode episode) {

		episodeNameEnText.setText(episode.getNameEn());
		episodeNameRuText.setText(episode.getNameRu());

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

	private void setSystemUiVisibilityVisible(boolean visible) {

		int systemUiVisibility = 0;

		if (!visible) {

			systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

			if (Build.VERSION.SDK_INT > 15) {
				systemUiVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN;
			}

		}

		getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
	}

	private void switchControlsVisibility() {

		int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();

		boolean isVisible = isUiVisible(systemUiVisibility);

		setSystemUiVisibilityVisible(!isVisible);
	}

	private boolean isUiVisible(int systemUiVisibility) {
		return (systemUiVisibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
	}

}
