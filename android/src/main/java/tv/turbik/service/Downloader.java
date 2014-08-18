package tv.turbik.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author Pavel Savinov
 * @version 16/08/14 21:54
 */
public class Downloader extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

}
