package persistence;

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
import java.util.StringTokenizer;

import model.AbstractCommitment;
import model.Calendar;
import model.Commitment;
import model.ICalendar;
import model.IPreference;
import model.ITrip;
import model.Position;
import model.Trip;

public class PersonalDataVault implements IPersonalDataVault {
	
	private List<IPreference> preferences;

	public PersonalDataVault() {
		this.preferences = new ArrayList<>();
	}

	public List<ITrip> getAllTrip() {
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
	
	private ITrip parseTrip (String line) {
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
	

	public void addTrip(ITrip trip)  {
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

	public ICalendar getCalendar() {
		String fileName = "Calendar.txt";

		List<AbstractCommitment> commitments = new ArrayList<>();
		BufferedReader br  = null;
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
			throw new IllegalArgumentException("preferences must be initialized");
		this.preferences = preferences;
	}

	@Override
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
}
