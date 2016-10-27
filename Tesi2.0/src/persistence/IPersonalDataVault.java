package persistence;

import java.time.LocalDateTime;
import java.util.List;


import model.*;

public interface IPersonalDataVault {
	
	
	//prevedere primitive diverse da queste che fanno le stesse cose,
	//ma prima di dare i dati fanno richiesta di un data consent ?
	
	
	public List<ITrip> getAllTrip();
	public void addTrip(ITrip trip);
	//addTrip usata in TripDetector
	public ICalendar getCalendar();
	public List<IPreference> getPreferences();
	//usato in suggester manager
	public void setPreferences(List<IPreference> preferences);
	//settate nel controller da quelle prese in input
	public Position getPositionByDate(LocalDateTime date);

}
