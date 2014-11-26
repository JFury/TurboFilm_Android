package tv.turbik;

import android.app.Application;

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
	}

	@AfterInject
	void afterInject() {
		client.addCookie(client.loadCookie());
	}

}
