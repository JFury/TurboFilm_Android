package tv.turbik.screens.main.series;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import tv.turbik.EventBus;
import tv.turbik.R;
import tv.turbik.client.SmartClient;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.exception.server.NotLoggedInException;
import tv.turbik.dao.Series;
import tv.turbik.events.OpenSeriesEvent;
import tv.turbik.screens.AuthActivity_;

/**
 * Created by swap_i on 12/10/14.
 */
@EFragment(R.layout.main_series)
public class SeriesFragment extends Fragment {

	private static final Logger L = LoggerFactory.getLogger(SeriesFragment.class.getSimpleName());

	private static final int AUTH_REQUEST = 0;

	@ViewById GridView grid;

	@Bean SmartClient client;
	@Bean EventBus eventBus;

	private ArrayAdapter<Series> adapter;

	@Override
	public void onResume() {
		super.onResume();
		loadData();
	}

	@AfterViews
	void afterViews() {

		final DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.icon_movie)
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();

		final ImageLoader imageLoader = ImageLoader.getInstance();

		adapter = new SeriesAdapter(grid.getContext(), imageLoader, options);

		grid.setAdapter(adapter);

		grid.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));

	}

	@ItemClick
	void gridItemClicked(int position) {
		Series series = adapter.getItem(position);
		eventBus.post(new OpenSeriesEvent(series));
	}

	@Background
	void loadData() {

		try {

			List<Series> series = client.getAllSeries(true);

			updateAdapter(series);

		} catch (NotLoggedInException e) {

			AuthActivity_.intent(this).startForResult(AUTH_REQUEST);

		} catch (TurboException e) {

			L.error("Parse error", e);

		}

	}

	@UiThread
	void updateAdapter(List<Series> seriesList) {
		adapter.clear();
		adapter.addAll(seriesList);
		adapter.notifyDataSetChanged();
	}

	@OnActivityResult(AUTH_REQUEST)
	void authResult(int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			loadData();
		}
	}

}
