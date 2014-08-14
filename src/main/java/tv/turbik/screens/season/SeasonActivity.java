package tv.turbik.screens.season;

import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.androidannotations.annotations.*;
import org.gigahub.turbofilm.R;
import tv.turbik.client.Images;
import tv.turbik.client.NotLoggedInException;
import tv.turbik.client.ParseException;
import tv.turbik.client.TurboFilmClient;
import tv.turbik.client.container.SeasonPage;
import tv.turbik.client.model.BasicEpisode;
import tv.turbik.screens.episode.EpisodeActivity_;
import tv.turbik.ui.SeasonNumberDialog;
import tv.turbik.ui.SeasonSelector;

import java.io.IOException;

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

	@Extra int id;
	@Extra String alias;

	@Bean TurboFilmClient client;

	private int season = 1;
	private ArrayAdapter<BasicEpisode> adapter;

	@AfterViews
	void afterViews() {

		ImageLoader.getInstance().displayImage(Images.seriesBigPoster(id), poster);

		seasonSelector.setSeasonListener(this);

		adapter = new BasicEpisodeArrayAdapter(this);

		grid.setAdapter(adapter);

		loadData();
	}

	@ItemClick
	void gridItemClicked(int position) {
		BasicEpisode episode = adapter.getItem(position);
		EpisodeActivity_.intent(this)
				.alias(alias)
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
			SeasonPage seasonPage = client.getSeason(alias, season);
			update(seasonPage);

		} catch (NotLoggedInException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@UiThread
	void update(SeasonPage page) {

		nameEnText.setText(page.getSeriesNameEn());
		nameRuText.setText(page.getSeriesNameRu());

		adapter.clear();
		adapter.addAll(page.getEpisodes());
		adapter.notifyDataSetChanged();
	}

}