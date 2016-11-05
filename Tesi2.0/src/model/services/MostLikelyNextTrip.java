package model.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.MyData.IDataSet;
import model.mapfeatures.ITrip;
import model.mapfeatures.Position;
import model.registry.IMetadata;
import model.registry.Metadata;
import model.userdata.ICalendar;
import model.userdata.IPreference;
import model.userdata.suggestions.SuggesterManager;

public class MostLikelyNextTrip extends AbstractService {
	 private final String name = "Most Likely Next Trip";
	 private final Set<IMetadata> identifiers = new HashSet<IMetadata>();
	 private IDataSet dataSet;
	 private SuggesterManager suggesterManager;
	 
	public MostLikelyNextTrip() {
		super();
		this.suggesterManager = SuggesterManager.getInstance();
	}

	@Override
	protected Object concreteService(IDataSet dataSet) throws FileNotFoundException, IOException {
		this.dataSet = dataSet;
		return this.suggesterManager.getSuggestions((LocalDateTime) dataSet.get(Metadata.DATE_CONST),
				(Position) dataSet.get(Metadata.POSITION_CONST),
				(List<IPreference>) dataSet.get(Metadata.PREFERENCE_CONST),
				(List<ITrip>) dataSet.get(Metadata.TRIP_CONST), (ICalendar) dataSet.get(Metadata.CALENDAR_CONST));
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
