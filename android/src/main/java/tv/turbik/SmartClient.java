package tv.turbik;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import tv.turbik.client.TurbikClient;

/**
 * @author Pavel Savinov
 * @version 17/08/14 00:06
 */
@EBean(scope = EBean.Scope.Singleton)
public class SmartClient {

	@Bean TurbikClient client;

}
