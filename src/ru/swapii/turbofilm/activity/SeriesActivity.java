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
import ru.swapii.turbofilm.android.TurboApp;
import ru.swapii.turbofilm.TurboFilm;
import ru.swapii.turbofilm.android.R;
import ru.swapii.turbofilm.model.Series;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 05.05.12
 * Time: 0:49
 */
public class SeriesActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.series);

		final TurboFilm turbo = ((TurboApp) getApplication()).getTurbo();
		final DrawableCache drawableCache = ((TurboApp) getApplication()).getDrawableCache();

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					final List<Series> series = turbo.getSeries();

					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							((ListView) findViewById(R.id.seriesList)).setOnItemClickListener(new ListView.OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
									Intent intent = new Intent(SeriesActivity.this, EpisodesActivity.class);
									intent.putExtra("seriesPath", series.get(i).getPath());
									intent.putExtra("seriesSeasons", series.get(i).getSeasonsCount());
									startActivity(intent);
								}
							});

							ListView list = (ListView) findViewById(R.id.seriesList);
							list.setAdapter(new ArrayAdapter<Series>(SeriesActivity.this, 0, series) {
								@Override
								public View getView(int position, View convertView, ViewGroup parent) {

									final View view;

									if (convertView == null) {
										view = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.series_item, null);
									} else {
										view = convertView;
										view.findViewById(R.id.cover).setVisibility(View.INVISIBLE);
									}

									Series s = series.get(position);

									((TextView) view.findViewById(R.id.nameEn)).setText(s.getNameEn());
									((TextView) view.findViewById(R.id.nameRu)).setText(s.getNameRu());
									((TextView) view.findViewById(R.id.description)).setText(s.getDescription());
									((TextView) view.findViewById(R.id.seasonsCount)).setText(String.valueOf(s.getSeasonsCount()));
									((TextView) view.findViewById(R.id.episodesCount)).setText(String.valueOf(s.getEpisodesCount()));

									drawableCache.request(s.getMediumLogoUrl(), new FutureData<Drawable>() {
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
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NotLoggedInException e) {
					startActivity(new Intent(SeriesActivity.this, LoginActivity.class));
				}

			}
		}).start();


	}

}