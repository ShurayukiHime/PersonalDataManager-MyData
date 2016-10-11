package model;

import java.util.ArrayList;

public class Feature extends AbstractFeature {
	
	public Feature() {
		super();
	}
	
	public void insertTag (String key, String value) {
		if (key == null || key.length() == 0)
			throw new IllegalArgumentException("key must be true");
		if (value == null || value.length() == 0)
			throw new IllegalArgumentException("value must be true");
		
		if (key.equalsIgnoreCase("name")) {
			name = value;
		} else if (key.equalsIgnoreCase("website")) {
			this.webSite = value;
		} else {
			if (tags.get(key) == null)
				tags.put(key, new ArrayList<String>());
			if (!tags.get(key).contains(value))
				tags.get(key).add(value);
		}
	}

	@Override
	public String toString() {
		String result = "";
		if (name.length() > 0)
			result += "Name: " + name +", ";
		for (String key : tags.keySet()) {
			result += "Category: " + key +" [ ";
			for (String value : tags.get(key)) {
				result += value + " ";
			}
			result += "], ";
		}
		if (webSite.length() > 0)
			result += "WebSite: " + webSite + ", ";
		result += getPosition().toAddress();
		return result;
	}

	@Override
	public String toDestination() {
		return this.toString();
	}

}
