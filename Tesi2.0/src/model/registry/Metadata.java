package model.registry;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.mapfeatures.ITrip;
import model.mapfeatures.Position;
import model.userdata.ICalendar;
import model.userdata.IPreference;

public class Metadata implements IMetadata {
	// poichè per ipotesi i tipi di dato sono noti a priori, la mappa è riempita
	// con quei dati e non è possibile modificarla
	private Map<String, Class> mappings = new HashMap<String, Class>();

	public static final String CALENDAR_CONST = ICalendar.class.getCanonicalName();
	public static final String TRIP_CONST = ITrip.class.getCanonicalName();
	public static final String PREFERENCE_CONST = IPreference.class.getCanonicalName();
	public static final String POSITION_CONST = Position.class.getCanonicalName();
	public static final String DATE_CONST = Date.class.getCanonicalName();

	private Metadata() {
		mappings.put(CALENDAR_CONST, ICalendar.class);
		mappings.put(TRIP_CONST, ITrip.class);
		mappings.put(PREFERENCE_CONST, IPreference.class);
		mappings.put(POSITION_CONST, Position.class);
		mappings.put(DATE_CONST, Date.class);
	}
}
