package tv.turbik.client.exception.client;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 22.11.13 13:49
 */
public class ParseException extends ClientException {

	public ParseException(Throwable cause) {
		super(cause);
	}

	public ParseException(String detailMessage) {
		super(detailMessage);
	}

}
