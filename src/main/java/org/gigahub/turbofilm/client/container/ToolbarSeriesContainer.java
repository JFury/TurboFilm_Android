package org.gigahub.turbofilm.client.container;

import org.gigahub.turbofilm.client.model.BasicSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 22.11.13 13:38
 */
public class ToolbarSeriesContainer {

	private List<BasicSeries> mySeries = new ArrayList<BasicSeries>();
	private List<BasicSeries> otherSeries = new ArrayList<BasicSeries>();

	public List<BasicSeries> getMySeries() {
		return mySeries;
	}

	public List<BasicSeries> getOtherSeries() {
		return otherSeries;
	}

}
