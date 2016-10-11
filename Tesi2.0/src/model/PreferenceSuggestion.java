package model;

import java.util.ArrayList;
import java.util.List;

public class PreferenceSuggestion implements ISuggestion {
	
	private List<IPreference> preferences;
	private List<AbstractFeature> features;
	
	public PreferenceSuggestion(List<IPreference> preferences, List<AbstractFeature> features) {
		if (preferences == null || features == null)
			throw new IllegalArgumentException("preferences and features must be initialized");
		this.preferences = preferences;
		this.features = features;
	}

	@Override
	public List<IDestination> getDestinations() {
		List<IDestination> destinations = new ArrayList<>();
		
		for (AbstractFeature f : features)
			destinations.add(f);
		
		return destinations;
	}

	@Override
	public String getInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("Suggestions based on the following preferences:");
		sb.append('\n');
		for (IPreference p : preferences) {
			sb.append(p);
			sb.append(",");
		}
		return sb.toString();
	}

}
