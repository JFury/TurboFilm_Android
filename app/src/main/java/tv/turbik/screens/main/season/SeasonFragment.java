package tv.turbik.screens.main.season;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import tv.turbik.R;
import tv.turbik.beans.EventBus;
import tv.turbik.client.SmartClient;
import tv.turbik.client.exception.TurboException;
import tv.turbik.dao.Episode;
import tv.turbik.events.EpisodeSelectedEvent;
import tv.turbik.screens.episode.EpisodeActivity_;
import tv.turbik.ui.RecyclerViewColumnWidthTuner;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 12:38
 */
@EFragment(R.layout.main_season)
public class SeasonFragment extends Fragment {

	private static final Logger L = LoggerFactory.getLogger(SeasonFragment.class.getSimpleName());

	@ViewById RecyclerView grid;

	@FragmentArg String seriesAlias;
	@FragmentArg byte season = 1;

	@Bean EventBus eventBus;
	@Bean SmartClient client;

	private EpisodeListAdapter adapter;

	@AfterViews
	void afterViews() {

		grid.setAdapter(adapter = new EpisodeListAdapter());

		final GridLayoutManager gridLayoutManager = new GridLayoutManager(grid.getContext(), 1);
		grid.setLayoutManager(gridLayoutManager);

		new RecyclerViewColumnWidthTuner(grid, 200);
	}

	@Override
	public void onResume() {
		super.onResume();
		eventBus.register(this);
		loadData();
	}

	@Override
	public void onPause() {
		super.onPause();
		eventBus.unregister(this);
	}

	public void onEvent(EpisodeSelectedEvent event) {
		Episode episode = event.getEpisode();
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
