package tv.turbik.events;

import tv.turbik.dao.Series;

/**
 * Created by swap_i on 12/10/14.
 */
public class OpenSeriesEvent {

	private Series series;

	public OpenSeriesEvent(Series series) {
		this.series = series;
	}

	public Series getSeries() {
		return series;
	}

}
