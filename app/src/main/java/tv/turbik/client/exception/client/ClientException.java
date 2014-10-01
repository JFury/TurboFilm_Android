package tv.turbik.client.exception.client;

import tv.turbik.client.exception.TurboException;

/**
 * @author Pavel Savinov
 * @version 16/08/14 23:49
 */
public class ClientException extends TurboException {

	public ClientException(Throwable cause) {
		super(cause);
	}

	public ClientException(String detailMessage) {
		super(detailMessage);
	}

}
