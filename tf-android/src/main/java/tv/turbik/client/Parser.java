package tv.turbik.client;

import tv.turbik.client.exception.client.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 14:10
 */
public abstract class Parser {

	protected static String parseString(String text, Pattern pattern) throws ParseException {
		Matcher matcher = pattern.matcher(text);
		if (!matcher.find()) throw new ParseException("Can't parse input string");
		return matcher.group(1).trim();
	}

	protected int parseInt(String text, Pattern pattern) throws ParseException {
		Matcher matcher = pattern.matcher(text);
		if (!matcher.find()) throw new ParseException("Can't parse input string");
		return Integer.parseInt(matcher.group(1));
	}

}
