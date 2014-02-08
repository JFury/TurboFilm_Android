package org.gigahub.turbofilm;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.gigahub.turbofilm.client.TurboFilmClient;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 20.11.13 15:51
 */
public class TurboModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(TurboFilmClient.class).asEagerSingleton();
	}

}
