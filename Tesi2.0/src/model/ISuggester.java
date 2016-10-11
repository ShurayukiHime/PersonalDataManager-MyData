package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ISuggester {

	/**
	 * @return the best suggestion for the given situation
	 */
	List<ISuggestion> getSuggestions() throws FileNotFoundException, IOException;

}
