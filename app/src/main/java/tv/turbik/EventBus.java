package tv.turbik;

import org.androidannotations.annotations.EBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by swap_i on 12/10/14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EventBus {

	private static final Logger L = LoggerFactory.getLogger(EventBus.class.getSimpleName());

	private com.google.common.eventbus.EventBus bus = new com.google.common.eventbus.EventBus();

	public void register( Object receiver) {
		bus.register(receiver);
	}

	public void unregister( Object receiver) {
		bus.unregister(receiver);
	}

	public void post( Object event) {
		bus.post(event);
	}

}
