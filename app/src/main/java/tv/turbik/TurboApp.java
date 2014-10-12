package tv.turbik;

import android.app.Application;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tv.turbik.client.TurboFilmClient;

/**
 * @author Pavel Savinov aka swap_i
 * @since 1.0.0
 */
@EApplication
public class TurboApp extends Application {

	private static final Logger L = LoggerFactory.getLogger(TurboApp.class.getSimpleName());

	@Bean TurboFilmClient client;

	@Override
	public void onCreate() {
		super.onCreate();

		L.info("-");
		L.info("-");
		L.info("-");
		L.warn("TurboFilm started");

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

	@AfterInject
	void afterInject() {
		client.addCookie(client.loadCookie());
	}

}
