package org.gigahub.turbofilm.screens.home;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import com.actionbarsherlock.app.SherlockActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import org.androidannotations.annotations.*;
import org.gigahub.turbofilm.R;
import org.gigahub.turbofilm.client.NotLoggedInException;
import org.gigahub.turbofilm.client.ParseException;
import org.gigahub.turbofilm.client.TurboFilmClient;
import org.gigahub.turbofilm.client.container.HomePage;
import org.gigahub.turbofilm.client.model.BasicSeries;
import org.gigahub.turbofilm.screens.AuthActivity_;
import org.gigahub.turbofilm.screens.season.SeasonActivity_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Pavel Savinov aka swap_i
 * @since 1.0.0
 */
@EActivity(R.layout.home)
public class HomeActivity extends SherlockActivity {

	private static final Logger L = LoggerFactory.getLogger(HomeActivity.class.getSimpleName());
	private static final int AUTH_REQUEST = 0;

	@ViewById GridView grid;

	@Bean TurboFilmClient client;

	private ArrayAdapter<BasicSeries> adapter;

	@AfterViews
	void afterViews() {

		final DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.icon_movie)
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();


		final ImageLoader imageLoader = ImageLoader.getInstance();

		adapter = new BasicSeriesArrayAdapter(this, imageLoader, options);

		grid.setAdapter(adapter);

		grid.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));

		loadData();
	}

	@ItemClick
	void gridItemClicked(int position) {
		BasicSeries series = adapter.getItem(position);
		SeasonActivity_.intent(this).alias(series.getAlias()).start();
	}

	@Background
	void loadData() {

		try {

			HomePage homePage = client.getHome();

			updateAdapter(homePage);

		} catch (NotLoggedInException e) {

			AuthActivity_.intent(HomeActivity.this).startForResult(AUTH_REQUEST);

		} catch (IOException e) {

			L.error("IO error", e);

		} catch (ParseException e) {

			L.error("Parse error", e);

		}

	}

	@UiThread
	void updateAdapter(HomePage homePage) {
		adapter.clear();
		adapter.addAll(homePage.getTopSeries().getMySeries());
		adapter.addAll(homePage.getTopSeries().getOtherSeries());
		adapter.notifyDataSetChanged();
	}

	@OnActivityResult(AUTH_REQUEST)
	void authResult(int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			loadData();
		}
	}

}
