package tv.turbik.screens.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import org.androidannotations.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.turbik.R;
import tv.turbik.client.SmartClient;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.exception.server.NotLoggedInException;
import tv.turbik.dao.Series;
import tv.turbik.screens.AuthActivity_;
import tv.turbik.screens.season.SeasonActivity_;

import java.util.List;

/**
 * @author Pavel Savinov aka swap_i
 * @since 1.0.0
 */
@EActivity(R.layout.home)
public class HomeActivity extends FragmentActivity {

	private static final Logger L = LoggerFactory.getLogger(HomeActivity.class.getSimpleName());
	private static final int AUTH_REQUEST = 0;

	@ViewById GridView grid;

	@Bean SmartClient client;

	private ArrayAdapter<Series> adapter;

	@AfterViews
	void afterViews() {

		final DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.icon_movie)
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();


		final ImageLoader imageLoader = ImageLoader.getInstance();

		adapter = new SeriesAdapter(this, imageLoader, options);

		grid.setAdapter(adapter);

		grid.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));

		loadData();
	}

	@ItemClick
	void gridItemClicked(int position) {
		Series series = adapter.getItem(position);
		SeasonActivity_.intent(this)
				.seriesAlias(series.getAlias())
				.start();
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
		if (resultCode == RESULT_OK) {
			loadData();
		}
	}

}
