package org.gigahub.turbofilm;

import org.junit.Assert;

import org.junit.Test;

/**
 * Created by swap_i on 14/02/14.
 */
public class TestUtils {

	@Test
	public void testConvertTime() {

		Assert.assertEquals("00:00:59", Utils.convertTime(59000));

		Assert.assertEquals("00:01:59", Utils.convertTime(119000));

		Assert.assertEquals("01:00:59", Utils.convertTime(3659000));

	}

}
