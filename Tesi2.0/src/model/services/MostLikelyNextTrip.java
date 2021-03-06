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
import model.registry.Metadata;
import model.registry.ServiceRegistry;
import model.userdata.ICalendar;
import model.userdata.IPreference;
import model.userdata.suggestions.SuggesterManager;

/**
 * This is an example of MyData compliant service. The business logic could be
 * implemented here or separated in another class (as showed in this example).
 * In any case, each service must define its own name and Set of identifiers,
 * since they are required for identification and registration.
 * 
 * @author Giada
 *
 */

public class MostLikelyNextTrip extends AbstractService {
	private final String name = "Most Likely Next Trip";
	private final Set<String> identifiers = new HashSet<String>();
	private SuggesterManager suggesterManager;

	public MostLikelyNextTrip() {
		super();
		this.suggesterManager = SuggesterManager.getInstance();
		this.registerService();
	}

	@Override
	protected Object concreteService(IDataSet dataSet) throws FileNotFoundException, IOException {
		return this.suggesterManager.getSuggestions((LocalDateTime) dataSet.getObject(Metadata.DATE_CONST),
				(Position) dataSet.getObject(Metadata.POSITION_CONST),
				(List<IPreference>) dataSet.getObject(Metadata.PREFERENCE_CONST),
				(List<ITrip>) dataSet.getObject(Metadata.TRIP_CONST),
				(ICalendar) dataSet.getObject(Metadata.CALENDAR_CONST));
	}

	/**
	 * In this function, each concrete service specifies which of the allowed
	 * types it is going to use. The Service Registry must be informed at the
	 * end of this process.
	 */
	@Override
	protected void registerService() {
		identifiers.add(Metadata.CALENDAR_CONST);
		identifiers.add(Metadata.DATE_CONST);
		identifiers.add(Metadata.POSITION_CONST);
		identifiers.add(Metadata.PREFERENCE_CONST);
		identifiers.add(Metadata.TRIP_CONST);
		ServiceRegistry.registerService(this, identifiers);
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

	/*
	 * public void setName(String name) { if (this.name == null) this.name =
	 * name; }
	 */
}
