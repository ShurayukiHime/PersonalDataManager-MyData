package model.consents;

import java.time.LocalDateTime;
import java.util.List;

import model.mapfeatures.ITrip;
import model.mapfeatures.Position;
import model.userdata.ICalendar;
import model.userdata.IPreference;

public interface IDataSet {

	public LocalDateTime getTodaysDate();

	public Position getActualPosition();

	public List<IPreference> getAllPreferences();

	public List<ITrip> getAllTrips();

	public ICalendar getCalendar();

}
