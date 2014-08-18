package tv.turbik.client.exception;

/**
 * @author Pavel Savinov
 * @version 16/08/14 23:48
 */
public class TurboException extends Exception {

	public TurboException(Throwable cause) {
		super(cause);
	}

	public TurboException(String detailMessage) {
		super(detailMessage);
	}

	public TurboException() {
	}

}
