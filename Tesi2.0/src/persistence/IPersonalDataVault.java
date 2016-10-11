package persistence;

import java.time.LocalDateTime;
import java.util.List;


import model.*;

public interface IPersonalDataVault {
	
	public List<ITrip> getAllTrip();
	public void addTrip(ITrip trip);
	public ICalendar getCalendar();
	public List<IPreference> getPreferences();
	public void setPreferences(List<IPreference> preferences);
	public Position getPositionByDate(LocalDateTime date);

}
