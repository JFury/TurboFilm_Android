package tv.turbik.screens.main.series;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import tv.turbik.beans.EventBus;
import tv.turbik.R;
import tv.turbik.client.SmartClient;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.exception.server.NotLoggedInException;
import tv.turbik.dao.Series;
import tv.turbik.screens.AuthActivity_;
import tv.turbik.ui.RecyclerViewColumnWidthTuner;

/**
 * Created by swap_i on 12/10/14.
 */
@EFragment(R.layout.main_series)
public class SeriesFragment extends Fragment {

	private static final Logger L = LoggerFactory.getLogger(SeriesFragment.class.getSimpleName());

	private static final int AUTH_REQUEST = 0;

	@ViewById RecyclerView grid;

	@Bean SmartClient client;
	@Bean EventBus eventBus;

	private SeriesListAdapter adapter;

	@Override
	public void onResume() {
		super.onResume();
		loadData();
	}

	@AfterViews
	void afterViews() {

		GridLayoutManager gridLayoutManager = new GridLayoutManager(grid.getContext(), 2);

		grid.setAdapter(adapter = new SeriesListAdapter());
		grid.setLayoutManager(gridLayoutManager);

		new RecyclerViewColumnWidthTuner(grid, 250);
	}

	@Background
	void loadData() {

		try {

			List<Series> series = client.getAllSeries(false);

			updateAdapter(series);

		} catch (NotLoggedInException e) {

			AuthActivity_.intent(this).startForResult(AUTH_REQUEST);

		} catch (TurboException e) {

			L.error("Parse error", e);

		}

	}

	@UiThread
	void updateAdapter(List<Series> seriesList) {
		adapter.addSeries(seriesList);
		adapter.notifyDataSetChanged();
	}

	@OnActivityResult(AUTH_REQUEST)
	void authResult(int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			loadData();
		}
	}

}
