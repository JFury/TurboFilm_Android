package ru.swapii.turbofilm.android;

import android.app.Application;
import ru.swapii.turbofilm.DrawableCache;
import ru.swapii.turbofilm.TurboFilm;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 18.08.12
 * Time: 11:01
 */
public class TurboApp extends Application {

	private TurboFilm turbo;
	private DrawableCache drawableCache;

	public TurboApp() {
		turbo = new TurboFilm();
		drawableCache = new DrawableCache();
	}

	public TurboFilm getTurbo() {
		return turbo;
	}

	public DrawableCache getDrawableCache() {
		return drawableCache;
	}

}
