package org.gigahub.turbofilm.screens.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import org.gigahub.turbofilm.R;
import org.gigahub.turbofilm.screens.AuthActivity;
import org.gigahub.turbofilm.screens.season.SeasonIntent;
import org.gigahub.turbofilm.client.Images;
import org.gigahub.turbofilm.client.NotLoggedInException;
import org.gigahub.turbofilm.client.ParseException;
import org.gigahub.turbofilm.client.TurboFilmClient;
import org.gigahub.turbofilm.client.container.HomePage;
import org.gigahub.turbofilm.client.model.BasicSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Pavel Savinov aka swap_i
 * @since 1.0.0
 */
@ContentView(R.layout.home)
public class HomeActivity extends RoboActivity {

	private static final Logger L = LoggerFactory.getLogger(HomeActivity.class.getSimpleName());
	private static final int AUTH_REQUEST = 0;

	@InjectView(R.id.grid) GridView gridView;

	@Inject TurboFilmClient client;

	private ArrayAdapter<BasicSeries> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		final DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.icon_movie)
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();


		final ImageLoader imageLoader = ImageLoader.getInstance();

		adapter = new ArrayAdapter<BasicSeries>(this, 0) {
			@Override
			public View getView(int position, View view, ViewGroup parent) {

				BasicSeries series = getItem(position);

				if (view == null) {
					view = getLayoutInflater().inflate(R.layout.home_series_item, parent, false);
				}

				final ImageView imageView = (ImageView) view.findViewById(R.id.logo);

				imageLoader.displayImage(Images.seriesBigPoster(series.getId()), imageView, options, new SimpleImageLoadingListener(){
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
					}
				});

				((TextView) view.findViewById(R.id.nameEnText)).setText(series.getNameEn());
				((TextView) view.findViewById(R.id.nameRuText)).setText(series.getNameRu());

				return view;
			}
		};

		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				BasicSeries series = adapter.getItem(position);
				startActivity(new SeasonIntent(HomeActivity.this, series));
			}
		});

		gridView.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));

		gridView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				gridView.setNumColumns(Math.max(v.getMeasuredWidth() / 600, 1));
			}
		});

		loadData();
	}

	private void loadData() {

		new AsyncTask<Void, Void, HomePage>() {
			@Override
			protected HomePage doInBackground(Void... params) {

				HomePage homePage = null;

				try {

					homePage = client.getHome();

				} catch (NotLoggedInException e) {

					cancel(true);
					startActivityForResult(new Intent(HomeActivity.this, AuthActivity.class), AUTH_REQUEST);

				} catch (IOException e) {

					L.error("IO error", e);
					cancel(true);

				} catch (ParseException e) {

					L.error("Parse error", e);
					cancel(true);

				}

				return homePage;
			}

			@Override
			protected void onPostExecute(HomePage homePage) {
				adapter.clear();
				adapter.addAll(homePage.getTopSeries().getMySeries());
				adapter.addAll(homePage.getTopSeries().getOtherSeries());
				adapter.notifyDataSetChanged();
			}

		}.execute();

	}

	@Override
	protected void onResume() {
		super.onResume();
		L.trace("Home resume");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AUTH_REQUEST && resultCode == RESULT_OK) {
			loadData();
		}
	}
}
