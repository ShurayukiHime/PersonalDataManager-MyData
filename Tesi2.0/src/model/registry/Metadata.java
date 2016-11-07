package model.registry;

import java.util.Date;

import model.mapfeatures.ITrip;
import model.mapfeatures.Position;
import model.userdata.ICalendar;
import model.userdata.IPreference;

public final class Metadata implements IMetadata {
	
	public static final String CALENDAR_CONST = ICalendar.class.getCanonicalName();
	public static final String TRIP_CONST = ITrip.class.getCanonicalName();
	public static final String PREFERENCE_CONST = IPreference.class.getCanonicalName();
	public static final String POSITION_CONST = Position.class.getCanonicalName();
	public static final String DATE_CONST = Date.class.getCanonicalName();

	private Metadata() {
	}
}
