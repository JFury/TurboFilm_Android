package tv.turbik.client.home;

import tv.turbik.client.toolbar.ToolbarContainer;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 20.11.13 15:23
 */
public class HomePage {

	private ToolbarContainer toolbarContainer;

	public void setToolbarContainer(ToolbarContainer topSeries) {
		this.toolbarContainer = topSeries;
	}

	public ToolbarContainer getToolbarContainer() {
		return toolbarContainer;
	}

}
