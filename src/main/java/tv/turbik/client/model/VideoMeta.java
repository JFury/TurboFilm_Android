package tv.turbik.client.model;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 24.11.13 22:12
 */
public class VideoMeta {

	private String defaultSource;
	private String hqSource;
	private int aspect;
	private String screen;
	private int eid;

	public String getDefaultSource() {
		return defaultSource;
	}

	public void setDefaultSource(String defaultSource) {
		this.defaultSource = defaultSource;
	}

	public String getHqSource() {
		return hqSource;
	}

	public void setHqSource(String hqSource) {
		this.hqSource = hqSource;
	}

	public int getAspect() {
		return aspect;
	}

	public void setAspect(int aspect) {
		this.aspect = aspect;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

}