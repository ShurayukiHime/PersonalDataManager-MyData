package model.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import model.consents.IDataSet;
import model.registry.IMetadatum;
import model.userdata.suggestions.SuggesterManager;

public class MostLikelyNextTrip extends AbstractService {
	 private final String name = "Most Likely Next Trip";
	 private final Set<IMetadatum> identifiers = new HashSet<IMetadatum>();
	 private IDataSet dataSet;
	 private SuggesterManager suggesterManager;
	 
	 public public MostLikelyNextTrip() {
		super();
		this.suggesterManager = SuggesterManager.getInstance();
	}

	@Override
	protected Object concreteService(IDataSet dataSet) throws FileNotFoundException, IOException {
		this.dataSet = dataSet;
		return this.suggesterManager.getSuggestions(dataSet.getTodaysDate(), dataSet.getActualPosition(), dataSet.getAllPreferences(), dataSet.getAllTrips());

	}

	@Override
	protected void registerService() {
		// TODO Auto-generated method stub

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MostLikelyNextTrip other = (MostLikelyNextTrip) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String toString() {
		return this.name;
	}
	
	/*public void setName(String name) {
	if (this.name == null)
		this.name = name;
	}*/
}
