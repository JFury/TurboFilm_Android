package tv.turbik.beans;

import org.androidannotations.annotations.EBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by swap_i on 12/10/14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EventBus extends de.greenrobot.event.EventBus {

}
