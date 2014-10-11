package tv.turbik.screens.season;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.androidannotations.annotations.*;
import tv.turbik.R;
import tv.turbik.client.Images;
import tv.turbik.client.SmartClient;
import tv.turbik.client.exception.TurboException;
import tv.turbik.dao.Episode;
import tv.turbik.dao.Series;
import tv.turbik.screens.episode.EpisodeActivity_;
import tv.turbik.ui.SeasonNumberDialog;
import tv.turbik.ui.SeasonSelector;

import java.util.List;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 12:38
 */
@EActivity(R.layout.season)
public class SeasonActivity extends ActionBarActivity {

	ImageView icon;
	TextView nameEn;
	TextView nameRu;
	SeasonSelector seasonSelector;

	@ViewById GridView grid;

	@Extra String seriesAlias;
	@Extra byte season = 1;

	@Bean SmartClient client;

	private ArrayAdapter<Episode> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getSupportActionBar();

		actionBar.setCustomView(R.layout.season_header);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);

		View view = actionBar.getCustomView();
		icon = (ImageView) view.findViewById(R.id.icon);
		nameEn = (TextView) view.findViewById(R.id.name_en);
		nameRu = (TextView) view.findViewById(R.id.name_ru);
		seasonSelector = (SeasonSelector) view.findViewById(R.id.season_selector);
	}

	@AfterViews
	void afterViews() {

		Series series = client.getSeries(seriesAlias);

		nameEn.setText(series.getNameEn());
		nameRu.setText(series.getNameRu());

		seasonSelector.setSeasonCount(series.getSeasonsCount());
		seasonSelector.setSeason(season);
		seasonSelector.setSeasonListener(new SeasonNumberDialog.SeasonListener() {
			@Override
			public void seasonSelected(byte season) {
				SeasonActivity_.intent(SeasonActivity.this)
						.seriesAlias(seriesAlias)
						.season(season)
						.start();
			}
		});

		ImageLoader.getInstance().displayImage(Images.seriesSmallSquare(series.getId()), icon);

		adapter = new EpisodeAdapter(this);
		grid.setAdapter(adapter);

		loadData();
	}

	@ItemClick
	void gridItemClicked(int position) {
		Episode episode = adapter.getItem(position);
		EpisodeActivity_.intent(this)
				.seriesAlias(episode.getSeriesAlias())
				.season(episode.getSeason())
				.episode(episode.getEpisode())
				.start();
	}

	@Background
	void loadData() {
		try {
			List<Episode> episodeList = client.getEpisodes(seriesAlias, season, false);
			update(episodeList);
		} catch (TurboException e) {
			e.printStackTrace();
		}
	}

	@UiThread
	void update(List<Episode> episodeList) {
		adapter.clear();
		adapter.addAll(episodeList);
		adapter.notifyDataSetChanged();
	}

}
