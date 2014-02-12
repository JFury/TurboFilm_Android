package org.gigahub.turbofilm.screens.episode;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.VideoView;

import org.gigahub.turbofilm.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by swap_i on 11/02/14.
 */
public class VideoControls extends Fragment {

	private static final Logger L = LoggerFactory.getLogger(VideoControls.class.getSimpleName());

	private VideoView video;

	private SeekBar seekBar;
	private ImageButton playButton;
	private ImageButton fullscreenButton;

	private Drawable playIcon;
	private Drawable pauseIcon;
	private boolean isFullscreen;
	private Timer timer;
	private ScheduledExecutorService executor;
	private boolean isSeeking;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		playIcon = getResources().getDrawable(R.drawable.ic_action_play);
		pauseIcon = getResources().getDrawable(R.drawable.ic_action_pause);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.video_controls, container);

		seekBar = (SeekBar) v.findViewById(R.id.seekBar);
		playButton = (ImageButton) v.findViewById(R.id.play);
		fullscreenButton = (ImageButton) v.findViewById(R.id.fullscreen);

		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (video != null) {
					if (video.isPlaying()) video.pause();
					else video.start();

					playButton.setImageDrawable(video.isPlaying() ? pauseIcon : playIcon);
				}
				resetTimer();
			}
		});

		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				resetTimer();
				isSeeking = true;
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (video != null) video.seekTo(seekBar.getProgress());
				resetTimer();
				isSeeking = false;
			}
		});

		fullscreenButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				isFullscreen = !isFullscreen;

				int i = View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
				getActivity().getWindow().getDecorView().setSystemUiVisibility(isFullscreen ? i : 0);

				if (isFullscreen) getActivity().getActionBar().hide();
				else getActivity().getActionBar().show();

				resetTimer();
			}
		});

		getActivity().getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
			@Override
			public void onSystemUiVisibilityChange(int i) {

				if ((i & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
					getActivity().getActionBar().show();
				} else {
					getActivity().getActionBar().hide();
				}

				resetTimer();

			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				if (video != null && !isSeeking) seekBar.setProgress(video.getCurrentPosition());
			}
		}, 0, 500, TimeUnit.MILLISECONDS);
	}

	@Override
	public void onDetach() {
		super.onDetach();

		if (executor != null) {
			executor.shutdown();
			executor = null;
		}
	}

	private void resetTimer() {

		L.trace("Reset timer");

		getView().setVisibility(View.VISIBLE);

		if (timer != null) {
			timer.cancel();
		}

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (video.isPlaying()) getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						getView().setVisibility(View.INVISIBLE);
					}
				});
			}
		}, 3000);

	}

	public void setVideo(VideoView video) {
		this.video = video;

		video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				seekBar.setMax(mp.getDuration());
			}
		});

		video.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				resetTimer();
				return true;
			}
		});

	}

}
