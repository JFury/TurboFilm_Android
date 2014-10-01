package tv.turbik.screens.episode;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import org.androidannotations.annotations.*;
import tv.turbik.R;
import tv.turbik.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by swap_i on 11/02/14.
 */
@EFragment(R.layout.video_controls)
public class VideoControls extends Fragment {

	private static final Logger L = LoggerFactory.getLogger(VideoControls.class.getSimpleName());

	private VideoView video;

	@ViewById SeekBar seekBar;
	@ViewById TextView pastTime;
	@ViewById TextView remainTime;
	@ViewById ImageButton playButton;

	private Drawable playIcon;
	private Drawable pauseIcon;
	private ScheduledExecutorService executor;

	private boolean isSeeking;
	private boolean isPlaying;
	private boolean isRemainShow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		playIcon = getResources().getDrawable(R.drawable.ic_action_play);
		pauseIcon = getResources().getDrawable(R.drawable.ic_action_pause);
	}

	@AfterViews
	void AfterViews() {

		int bottom = getResources().getDimensionPixelSize(getResources().getIdentifier("navigation_bar_height", "dimen", "android"));
		getView().setPadding(0, 0, 0, bottom);

		getView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

		getActivity().getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
			@Override
			public void onSystemUiVisibilityChange(int i) {
				if ((i & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0 || (i & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
					getView().setVisibility(View.VISIBLE);
					getActivity().getActionBar().show();
				} else {
					getView().setVisibility(View.INVISIBLE);
					getActivity().getActionBar().hide();
				}
			}
		});

		pastTime.setText(Utils.convertTime(0));
		remainTime.setText("-" + Utils.convertTime(0));
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

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				if (video != null && !isSeeking) {
					update(video.getCurrentPosition());
				}
			}
		}, 0, 250, TimeUnit.MILLISECONDS);
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
	void update(int position) {
		int duration = video.getDuration();

		seekBar.setMax(duration);
		seekBar.setProgress(position);

		pastTime.setText(Utils.convertTime(position));

		if (duration > 0) {

			if (isRemainShow)
				remainTime.setText(Utils.convertTime(position - duration));
			else
				remainTime.setText(Utils.convertTime(duration));
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if (executor != null) {
			executor.shutdown();
			executor = null;
		}
	}

	public void setVideo(VideoView video) {

		this.video = video;

		video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {

				((EpisodeActivity) getActivity()).videoPrepared(mp.getVideoWidth(), mp.getVideoHeight());

			}
		});

		video.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {

				int i = getActivity().getWindow().getDecorView().getSystemUiVisibility();

				if ((View.SYSTEM_UI_FLAG_FULLSCREEN & i) == 0 || (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION & i) == 0) {

					getActivity().getWindow().getDecorView().setSystemUiVisibility(i | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

				}

				return true;
			}
		});

	}

}
