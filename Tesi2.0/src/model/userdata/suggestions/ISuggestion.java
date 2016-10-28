package model.userdata.suggestions;

import java.util.List;

import model.userdata.IDestination;

public interface ISuggestion {
	
	List<IDestination> getDestinations();
	String getInfo();

}
