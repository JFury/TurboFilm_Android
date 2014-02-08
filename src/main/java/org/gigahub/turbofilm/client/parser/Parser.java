package org.gigahub.turbofilm.client.parser;

import org.gigahub.turbofilm.client.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 23.11.13 14:10
 */
public abstract class Parser {

	protected String getString(String text, Pattern pattern) throws ParseException {
		Matcher matcher = pattern.matcher(text);
		if (!matcher.find()) throw new ParseException();
		return matcher.group(1);
	}

}
