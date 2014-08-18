package tv.turbik.screens.season;

import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.androidannotations.annotations.*;
import tv.turbik.R;
import tv.turbik.client.Images;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.smart.SmartClient;
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
public class SeasonActivity extends SherlockActivity implements SeasonNumberDialog.SeasonListener {

	@ViewById ImageView poster;
	@ViewById TextView nameEnText;
	@ViewById TextView nameRuText;
	@ViewById SeasonSelector seasonSelector;
	@ViewById GridView grid;

	@Extra long id;
	@Extra int season = 1;

	@Bean SmartClient client;

	private ArrayAdapter<Episode> adapter;

	@AfterViews
	void afterViews() {

		seasonSelector.setSeasonListener(new SeasonNumberDialog.SeasonListener() {
			@Override
			public void seasonSelected(int season) {
				SeasonActivity_.intent(SeasonActivity.this)
						.id(id)
						.season(season)
						.start();
			}
		});

		ImageLoader.getInstance().displayImage(Images.seriesBigPoster(id), poster);

		adapter = new EpisodeAdapter(this);
		grid.setAdapter(adapter);

		Series series = client.getSeries(id);
		nameEnText.setText(series.getNameEn());
		nameRuText.setText(series.getNameRu());

		loadData();
	}

	@ItemClick
	void gridItemClicked(int position) {
		Episode episode = adapter.getItem(position);
		EpisodeActivity_.intent(this)
				//.alias(alias)
				.season(season)
				.episode(episode.getEpisode())
				.start();
	}

	@Override
	public void seasonSelected(int season) {
		this.season = season;
		loadData();
	}

	@Background
	void loadData() {

		try {
			List<Episode> episodeList = client.getEpisodes(id, season);
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