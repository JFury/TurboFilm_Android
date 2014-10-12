package tv.turbik.screens.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.google.common.eventbus.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tv.turbik.EventBus;
import tv.turbik.R;
import tv.turbik.events.OpenSeriesEvent;
import tv.turbik.screens.main.season.SeasonFragment;
import tv.turbik.screens.main.season.SeasonFragment_;
import tv.turbik.screens.main.series.SeriesFragment;
import tv.turbik.screens.main.series.SeriesFragment_;

/**
 * Created by swap_i on 12/10/14.
 */
@EActivity(R.layout.main)
public class MainActivity extends FragmentActivity {

	private static final Logger L = LoggerFactory.getLogger(MainActivity.class.getSimpleName());

	SeriesFragment seriesFragment = new SeriesFragment_();
	SeasonFragment seasonFragment = new SeasonFragment_();

	@Bean EventBus eventBus;

	@AfterInject
	void afterInject() {
		eventBus.register(this);
	}

	@AfterViews
	void afterViews() {
		showFragment(seriesFragment);
	}

	@Click(R.id.menu_logo)
	void logoClick() {
		L.info("Logo click");
	}

	@Subscribe
	public void onOpenSeries(OpenSeriesEvent event) {
		seasonFragment.setParams(event.getSeries().getAlias(), (byte) 1);
		showFragment(seasonFragment);
	}

	void showFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment)
				.commit();
	}

}
