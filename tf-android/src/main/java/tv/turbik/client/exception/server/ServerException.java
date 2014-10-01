package tv.turbik.client.exception.server;

import tv.turbik.client.exception.TurboException;

/**
 * @author Pavel Savinov
 * @version 16/08/14 23:49
 */
public class ServerException extends TurboException {

	public ServerException(Throwable cause) {
		super(cause);
	}

	public ServerException(String detailMessage) {
		super(detailMessage);
	}

	public ServerException() {
	}

}
