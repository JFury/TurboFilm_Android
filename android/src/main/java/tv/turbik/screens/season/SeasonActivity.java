package tv.turbik.screens.season;

import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
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
public class SeasonActivity extends SherlockActivity {

	@ViewById ImageView poster;
	@ViewById TextView nameEnText;
	@ViewById TextView nameRuText;
	@ViewById SeasonSelector seasonSelector;

	@ViewById GridView grid;

	@Extra String seriesAlias;
	@Extra byte season = 1;

	@Bean SmartClient client;

	private ArrayAdapter<Episode> adapter;

	@AfterViews
	void afterViews() {

		ActionBar actionBar = getSupportActionBar();

		actionBar.setCustomView(R.layout.season_bar);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);


		Series series = client.getSeries(seriesAlias);
		nameEnText.setText(series.getNameEn());
		nameRuText.setText(series.getNameRu());

		seasonSelector.setSeasonCount(series.getSeasonsCount());

		ImageLoader.getInstance().displayImage(Images.seriesSmallSquare(series.getId()), poster);

		adapter = new EpisodeAdapter(this);

		seasonSelector.setSeasonListener(new SeasonNumberDialog.SeasonListener() {
					@Override
					public void seasonSelected(byte season) {
						SeasonActivity_.intent(SeasonActivity.this)
								.seriesAlias(seriesAlias)
								.season(season)
								.start();
					}
				});
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
