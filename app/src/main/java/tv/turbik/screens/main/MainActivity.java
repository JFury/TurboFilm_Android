package tv.turbik.screens.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tv.turbik.R;
import tv.turbik.beans.EventBus;
import tv.turbik.events.OpenSeriesEvent;
import tv.turbik.screens.main.season.SeasonFragment;
import tv.turbik.screens.main.season.SeasonFragment_;
import tv.turbik.screens.main.series.SeriesFragment_;

/**
 * Created by swap_i on 12/10/14.
 */
@EActivity(R.layout.main)
public class MainActivity extends FragmentActivity {

	private static final Logger L = LoggerFactory.getLogger(MainActivity.class.getSimpleName());

	@Bean EventBus eventBus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.content_frame, SeriesFragment_.builder().build())
					.commit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		eventBus.register(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		eventBus.unregister(this);
	}

	public void onEvent(OpenSeriesEvent event) {
		SeasonFragment fragment = SeasonFragment_.builder()
				.seriesAlias(event.getSeries().getAlias())
				.season((byte) 1)
				.build();
		showFragment(fragment);
	}

	@Click(R.id.menu_logo)
	void logoClick() {
		L.info("Logo click");
	}

	private void showFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment)
				.addToBackStack(null)
				.commit();
	}

}
