package org.gigahub.turbofilm.screens.season;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.gigahub.turbofilm.R;
import org.gigahub.turbofilm.screens.episode.EpisodeIntent;
import org.gigahub.turbofilm.ui.SeasonNumberDialog;
import org.gigahub.turbofilm.ui.SeasonSelector;
import org.gigahub.turbofilm.client.Images;
import org.gigahub.turbofilm.client.NotLoggedInException;
import org.gigahub.turbofilm.client.ParseException;
import org.gigahub.turbofilm.client.TurboFilmClient;
import org.gigahub.turbofilm.client.container.SeasonPage;
import org.gigahub.turbofilm.client.model.BasicEpisode;
import org.gigahub.turbofilm.client.model.BasicSeries;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 12:38
 */
@ContentView(R.layout.season)
public class SeasonActivity extends RoboActivity implements SeasonNumberDialog.SeasonListener {

	private @InjectView(R.id.poster) ImageView poster;
	private @InjectView(R.id.nameEnText) TextView nameEnText;
	private @InjectView(R.id.nameRuText) TextView nameRuText;
	private @InjectView(R.id.seasonSelector) SeasonSelector seasonSelector;
	private @InjectView(R.id.grid) GridView episodesGrid;

	private @InjectExtra(SeasonIntent.ID) int id;
	private @InjectExtra(SeasonIntent.ALIAS) String alias;
	private @InjectExtra(SeasonIntent.NAME_EN) String nameEn;
	private @InjectExtra(SeasonIntent.NAME_RU) String nameRu;

	private @Inject TurboFilmClient client;

	private int season = 1;
	private ArrayAdapter<BasicEpisode> adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ImageLoader.getInstance().displayImage(Images.seriesBigPoster(id), poster);

		nameEnText.setText(nameEn);
		nameRuText.setText(nameRu);

		seasonSelector.setSeasonListener(this);

		adapter = new ArrayAdapter<BasicEpisode>(this, 0) {
			@Override
			public View getView(int position, View v, ViewGroup parent) {

				BasicEpisode e = getItem(position);

				if (v == null) v = getLayoutInflater().inflate(R.layout.season_episode, parent, false);

				ImageLoader.getInstance().displayImage(e.getSmallPosterUrl(), (ImageView) v.findViewById(R.id.frameImage));

				((TextView) v.findViewById(R.id.nameEnText)).setText(e.getNameEn());
				((TextView) v.findViewById(R.id.nameRuText)).setText(e.getNameRu());
				((TextView) v.findViewById(R.id.episodeText)).setText(String.valueOf(e.getEpisode()));

				return v;
			}
		};

		episodesGrid.setAdapter(adapter);

		episodesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				BasicSeries series = new BasicSeries();
				series.setId(SeasonActivity.this.id);
				series.setAlias(alias);
				series.setNameEn(nameEn);
				series.setNameRu(nameRu);
				startActivity(new EpisodeIntent(SeasonActivity.this, series, adapter.getItem(position)));
			}
		});

		episodesGrid.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				episodesGrid.setNumColumns(Math.max(1, v.getMeasuredWidth() / 400));
			}
		});

		loadData();
	}

	@Override
	public void seasonSelected(int season) {
		this.season = season;
		loadData();
	}

	private void loadData() {
		new AsyncTask<Integer, Void, SeasonPage>() {

			@Override
			protected SeasonPage doInBackground(Integer... params) {

				SeasonPage seasonPage = null;

				try {
					seasonPage = client.getSeason(alias, params[0]);
				} catch (NotLoggedInException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}

				return seasonPage;
			}

			@Override
			protected void onPostExecute(SeasonPage seasonPage) {
				adapter.clear();
				adapter.addAll(seasonPage.getEpisodes());
				adapter.notifyDataSetChanged();
			}

		}.execute(season);
	}

}