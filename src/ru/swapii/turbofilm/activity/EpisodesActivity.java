package ru.swapii.turbofilm.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ru.swapii.turbofilm.DrawableCache;
import ru.swapii.turbofilm.FutureData;
import ru.swapii.turbofilm.NotLoggedInException;
import ru.swapii.turbofilm.TurboFilm;
import ru.swapii.turbofilm.android.R;
import ru.swapii.turbofilm.android.TurboApp;
import ru.swapii.turbofilm.model.Episode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 16.09.12
 * Time: 23:30
 */
public class EpisodesActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.episodes);

		final TurboFilm turbo = ((TurboApp) getApplication()).getTurbo();
		final DrawableCache drawableCache = ((TurboApp) getApplication()).getDrawableCache();

		Intent intent = getIntent();

		final String path = intent.getStringExtra("seriesPath");
		final int seasonsCount = intent.getIntExtra("seriesSeasons", 1);

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {

					final List<Episode> episodes = new ArrayList<Episode>();

					for (int i = 1; i <= seasonsCount; i++) {
						episodes.addAll(turbo.getEpisodes(path, i));
					}

					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							GridView list = (GridView) findViewById(R.id.episodesGrid);
							list.setAdapter(new ArrayAdapter<Episode>(EpisodesActivity.this, 0, episodes) {

								@Override
								public View getView(int position, View convertView, ViewGroup parent) {

									final View view;

									if (convertView == null) {
										view = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.episode_item, null);
									} else {
										view = convertView;
										view.findViewById(R.id.cover).setVisibility(View.INVISIBLE);
									}

									Episode e = episodes.get(position);

									((TextView) view.findViewById(R.id.nameEn)).setText(e.getNameEn());
									((TextView) view.findViewById(R.id.nameRu)).setText(e.getNameRu());

									((TextView) view.findViewById(R.id.season)).setText(String.valueOf(e.getSeason()));
									((TextView) view.findViewById(R.id.episode)).setText(String.valueOf(e.getEpisode()));

									drawableCache.request(e.getCoverSmallPath(), new FutureData<Drawable>() {
										@Override
										public void done(final Drawable drawable) {
											runOnUiThread(new Runnable() {
												@Override
												public void run() {
													((ImageView) view.findViewById(R.id.cover)).setImageDrawable(drawable);
													view.findViewById(R.id.cover).setVisibility(View.VISIBLE);
												}
											});
										}
									});

									return view;
								}

							});

						}
					});

				} catch (NotLoggedInException e) {
					startActivity(new Intent(EpisodesActivity.this, LoginActivity.class));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

}