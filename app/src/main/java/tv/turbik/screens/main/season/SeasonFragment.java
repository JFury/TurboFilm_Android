package tv.turbik.screens.main.season;

import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import tv.turbik.R;
import tv.turbik.client.SmartClient;
import tv.turbik.client.exception.TurboException;
import tv.turbik.dao.Episode;
import tv.turbik.screens.episode.EpisodeActivity_;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 12:38
 */
@EFragment(R.layout.main_season)
public class SeasonFragment extends Fragment {

	@ViewById GridView grid;

	String seriesAlias;
	byte season = 1;

	@Bean SmartClient client;

	private ArrayAdapter<Episode> adapter;

	public void setParams(String seriesAlias, byte season) {
		this.seriesAlias = seriesAlias;
		this.season = season;
	}

	@AfterViews
	void afterViews() {

		adapter = new EpisodeAdapter(grid.getContext());
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
