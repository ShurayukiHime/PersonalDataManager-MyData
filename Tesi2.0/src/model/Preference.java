package model;

public class Preference implements IPreference {

	private String category, name; // in OpenStreetMap le feature sono espresse come <key,value>, qua vengono trattate come categoria e nome
	
	public Preference (String category, String name) {
		if (category == null || category.length() == 0)
			throw new IllegalArgumentException("category must be true");
		this.category = category;
		this.name = name;
	}
		
	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Category=" + category + ", Name=" + name;
	}

}
