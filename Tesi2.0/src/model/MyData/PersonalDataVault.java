package model.MyData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import model.mapfeatures.ITrip;
import model.mapfeatures.Position;
import model.mapfeatures.Trip;
import model.registry.Metadata;
import model.userdata.AbstractCommitment;
import model.userdata.Calendar;
import model.userdata.Commitment;
import model.userdata.ICalendar;
import model.userdata.IPreference;

public class PersonalDataVault implements IPersonalDataVault {

	private List<IPreference> preferences;
	private ICalendar calendar;
	private List<ITrip> trips;
	private Position actualPosition;
	private LocalDateTime today;

	public PersonalDataVault() {
		this.preferences = new ArrayList<>();
		this.trips = new ArrayList<>();

		this.calendar = readCalendar();
		this.trips = readAllTrips();
	}

	private List<ITrip> readAllTrips() {
		String fileName = "MyTrips.txt";
		List<ITrip> result = new ArrayList<ITrip>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;

			while ((line = br.readLine()) != null) {
				ITrip trip = parseTrip(line);
				result.add(trip);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private ITrip parseTrip(String line) {
		if (line == null || line.length() == 0)
			throw new IllegalArgumentException("line must be initialized");
		ITrip result = null;

		String[] data = line.split(",");

		if (data.length != 9)
			throw new IllegalArgumentException("data must be correct, line is uncorrect");

		double x_from = Double.parseDouble(data[0].trim());
		double y_from = Double.parseDouble(data[1].trim());
		double x_to = Double.parseDouble(data[2].trim());
		double y_to = Double.parseDouble(data[3].trim());
		Position from = new Position(x_from, y_from);
		Position to = new Position(x_to, y_to);

		int year = Integer.parseInt(data[4].trim());
		Month month = Month.valueOf(data[5].trim());
		int day = Integer.parseInt(data[6].trim());
		int hour = Integer.parseInt(data[7].trim());
		int min = Integer.parseInt(data[8].trim());
		LocalDateTime date = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hour, min));

		result = new Trip(from, to, date);

		return result;
	}

	public void addTrip(ITrip trip) {
		if (trip == null)
			throw new IllegalArgumentException("trip must be initialized");
		String fileName = "MyTrips.txt";

		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(fileName), true));
			pw.append(trip.toString());
			pw.append('\n');
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ICalendar readCalendar() {
		String fileName = "Calendar.txt";

		List<AbstractCommitment> commitments = new ArrayList<>();
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new FileReader(new File(fileName)));
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");

				double latitude = Double.parseDouble(st.nextToken().trim());
				double longitude = Double.parseDouble(st.nextToken().trim());
				Position position = new Position(latitude, longitude);

				int day = Integer.parseInt(st.nextToken().trim());
				int month = Integer.parseInt(st.nextToken().trim());
				int year = Integer.parseInt(st.nextToken().trim());
				int hour = Integer.parseInt(st.nextToken().trim());
				int min = Integer.parseInt(st.nextToken().trim());
				LocalDateTime date = LocalDateTime.of(year, month, day, hour, min);

				String description = st.nextToken().trim();

				AbstractCommitment commitment = new Commitment(description, date, position);
				commitments.add(commitment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Calendar(commitments);
	}

	public List<IPreference> getPreferences() {
		return this.preferences;
	}

	public void setPreferences(List<IPreference> preferences) {
		if (preferences == null)
			throw new IllegalArgumentException("Preferences must be initialized.");
		this.preferences = preferences;
	}

	public List<ITrip> getAllTrip() {
		return this.trips;
	}

	public ICalendar getCalendar() {
		return this.calendar;
	}

	private void setCalendar(ICalendar calendar) {
		this.calendar = calendar;
	}

	public Position getPositionByDate(LocalDateTime date) {
		List<ITrip> trips = getAllTrip();
		long record = -1;
		Position result = null;
		for (ITrip t : trips) {
			long millis = ChronoUnit.MILLIS.between(date, t.getDate());
			if (millis > 0 && (millis < record || record < 0)) {
				record = millis;
				result = t.getFrom();
			}
		}
		return result;
	}

	private Position getActualPosition() {
		return actualPosition;
	}

	private void setActualPosition(Position actualPosition) {
		this.actualPosition = actualPosition;
	}

	private void setDate(LocalDateTime localDateTime) {
		this.today = localDateTime;
	}

	@Override
	public IDataSet getData(Set<String> typesConst) {
		IDataSet dataSet = new DataSet();
		for (String typeConst : typesConst) {
			if (typeConst.equals(Metadata.CALENDAR_CONST))
				dataSet.put(typeConst, this.getCalendar());
			if (typeConst.equals(Metadata.DATE_CONST))
				dataSet.put(typeConst, this.getDate());
			if (typeConst.equals(Metadata.POSITION_CONST))
				dataSet.put(typeConst, this.getActualPosition());
			if (typeConst.equals(Metadata.PREFERENCE_CONST))
				dataSet.put(typeConst, this.getPreferences());
			if (typeConst.equals(Metadata.TRIP_CONST))
				dataSet.put(typeConst, this.getAllTrip());
		}
		return dataSet;
	}

	@Override
	public void saveData(IDataSet dataSet) {
		for (String typeConst : dataSet.getKeys()) {
			if (typeConst.equals(Metadata.CALENDAR_CONST))
				this.setCalendar((ICalendar) dataSet.getObject(typeConst));
			if (typeConst.equals(Metadata.DATE_CONST))
				this.setDate((LocalDateTime) dataSet.getObject(typeConst));
			if (typeConst.equals(Metadata.POSITION_CONST))
				this.setActualPosition((Position) dataSet.getObject(typeConst));
			if (typeConst.equals(Metadata.PREFERENCE_CONST))
				this.setPreferences((List<IPreference>) dataSet.getObject(typeConst));
			if (typeConst.equals(Metadata.TRIP_CONST))
				this.addTrip((ITrip) dataSet.getObject(typeConst));
		}
	}

	private LocalDateTime getDate() {
		return this.today;
	}
}
