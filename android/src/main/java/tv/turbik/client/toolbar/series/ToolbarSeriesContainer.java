package tv.turbik.client.toolbar.series;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov
 * @version 19/08/14 21:43
 */
public class ToolbarSeriesContainer {

	private List<ToolbarSeries> mySeries = new ArrayList<ToolbarSeries>();
	private List<ToolbarSeries> otherSeries = new ArrayList<ToolbarSeries>();

	public List<ToolbarSeries> getMySeries() {
		return mySeries;
	}

	public List<ToolbarSeries> getOtherSeries() {
		return otherSeries;
	}

}
