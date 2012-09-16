package ru.swapii.turbofilm;

import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 20.08.12
 * Time: 21:52
 */
public interface FutureData<T> {

	void done(T result);

}
