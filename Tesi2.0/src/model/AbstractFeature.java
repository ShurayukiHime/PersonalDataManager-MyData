package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractFeature implements IDestination {
	
	private Position position;
	protected String name, webSite;
	protected Map<String, List<String>> tags;
	
	public AbstractFeature () {
		this.tags  = new HashMap<String, List<String>>();
		this.name = "";
		this.webSite = "";
		this.position = null;
	}

	/**
	 * @return the location of this feature
	 */
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		if (position == null)
			throw new IllegalArgumentException("position must be initialized");
		this.position = position;
	}
	
	public Map<String,List<String>> getTags() {
		return tags;
	}
	
	public String getWebSite() {
		return webSite;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void insertTag (String key, String value);

}
