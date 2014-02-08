package org.gigahub.turbofilm;

import android.app.Application;
import com.google.inject.Injector;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.apache.http.cookie.Cookie;
import org.gigahub.turbofilm.client.TurboFilmClient;
import roboguice.RoboGuice;

/**
 * @author Pavel Savinov aka swap_i
 * @since 1.0.0
 */
public class TurboApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Injector injector = RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE, RoboGuice.newDefaultRoboModule(this), new TurboModule());

		Settings settings = injector.getInstance(Settings.class);
		settings.init(this);

		Cookie cookie = settings.loadCookie();
		if (cookie != null) injector.getInstance(TurboFilmClient.class).addCookie(cookie);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheOnDisc(true)
				.cacheInMemory(true)
				.resetViewBeforeLoading(true)
				.build();

		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
				.defaultDisplayImageOptions(options)
				.discCacheSize(1024 * 1024 * 512)
				.build();

		ImageLoader.getInstance().init(configuration);
	}

}
