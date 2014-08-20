package tv.turbik.client.series;

import tv.turbik.client.toolbar.ToolbarContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov
 * @version 19/08/14 00:04
 */
public class SeriesPage {

	ToolbarContainer toolbarContainer;

	List<SeriesPageSeries> seriesList = new ArrayList<SeriesPageSeries>();

	public ToolbarContainer getToolbarContainer() {
		return toolbarContainer;
	}

	public void setToolbarContainer(ToolbarContainer toolbarContainer) {
		this.toolbarContainer = toolbarContainer;
	}

	public List<SeriesPageSeries> getSeriesList() {
		return seriesList;
	}

	public void setSeriesList(List<SeriesPageSeries> seriesList) {
		this.seriesList = seriesList;
	}

}
