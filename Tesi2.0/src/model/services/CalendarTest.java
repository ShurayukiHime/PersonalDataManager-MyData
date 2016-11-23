package model.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import model.MyData.IDataSet;
import model.registry.Metadata;
import model.registry.ServiceRegistry;
import model.userdata.ICalendar;

/**
 * This class is a replacement for program.CalendarTest, which isn't
 * MyData-compatible. In this perspective, the field date is exactly the same
 * one used in the old class.
 * 
 * @author Giada
 *
 */

public class CalendarTest extends AbstractService {
	private final String name = "Calendar Test";
	private LocalDateTime date;
	private final Set<String> identifiers = new HashSet<String>();

	public CalendarTest() {
		super();
		date = LocalDateTime.of(2016,9,8,15,30);
		this.registerService();
	}

	@Override
	protected Object concreteService(IDataSet dataSet) throws FileNotFoundException, IOException {
		ICalendar calendar = (ICalendar) dataSet.getObject(Metadata.CALENDAR_CONST);
		return calendar.getCommitmentByDate(date);
	}

	@Override
	protected void registerService() {
		this.identifiers.add(Metadata.CALENDAR_CONST);
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
		CalendarTest other = (CalendarTest) obj;
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

}
