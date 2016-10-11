package model;

import java.util.ArrayList;
import java.util.List;

public class HistorySuggestion implements ISuggestion {

	private List<IDestination> destinations;
	
	public HistorySuggestion() {
		this.destinations = new ArrayList<>();
	}
	
	public HistorySuggestion(HistoryDestination destination) {
		this();
		if (destination == null)
			throw new IllegalArgumentException("destination must be initialized");
		this.destinations.add(destination);
	}
		
	@Override
	public List<IDestination> getDestinations() {
		return destinations;
	}

	@Override
	public String getInfo() {
		return "suggestions are based on the past";		
	}

}
