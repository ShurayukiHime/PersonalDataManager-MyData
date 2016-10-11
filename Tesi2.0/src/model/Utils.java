package model;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class Utils {

	/**
	 * @param a the first instant
	 * @param b the second instant
	 * @return the difference between the two day_instants, expressed in minutes
	 */
	public static int deltaTimeMinute (LocalDateTime a, LocalDateTime b) {
		if (a == null || b == null)
			throw new IllegalArgumentException("a and b must be initialized");
		int result = Math.abs((a.getHour()*60 + a.getMinute()) - (b.getHour()*60 + b.getMinute()));
		if (result > 720) // 12*60 (12 ore)
			result = 1440 - result; // 24h - quelle risultanti
		// in pratica devo restituire sempre un numero inferiore a 12h, perchè l'orario è circolare
		// NON tengo volutamente conto della distanza tra i giorni in quanto mi serve espressamente la distanza tra i due LocalTime
		return result;
	}
	
	/**
	 * @param a is the first day 
	 * @param b is the second day
	 * @return the difference between the two dates expressed in days
	 */
	private static long deltaDays (LocalDateTime a, LocalDateTime b) {
		return Math.abs(ChronoUnit.DAYS.between(a, b));
	}
	
	/**
	 * @param today: is the reference day
	 * @param date: is the date that contains the day that needs to compute the value, closer to today is date, higher is the value
	 * @param days: is the number of days that are included into the filtering
	 * @return a value that is used to give a weight to a day
	 */
	public static double dayValue (LocalDateTime today, LocalDateTime date, int days) {
		if (today == null || date == null)
			throw new IllegalArgumentException("today and date must be initialized");
		return Utils.dayValue((int)Utils.deltaDays(today, date), days);
	}
	
	/**
	 * @param day represents the n-th day before the reference date
	 * @param days: is the number of days that are included into the filtering
	 * @return a value that is used to give a weight to a day
	 */
	public static double dayValue(int day, int days) {
		double result = 0;
		if (days == 30) {
			result = 1.975*(Math.pow(Math.E,-0.05*day)); // trasformazione di un'esponenziale di area 30
		} else if (days == 70) {
			result = 2.427*(Math.pow(Math.E,-0.03*day)); // trasformazione di un'esponenziale di area 70
		} else {
			throw new IllegalArgumentException("in dayValue method, days parameter must be 30 or 70");
		}
		return result;
	}
	
	/**
	 * This method downloads a file from a specified url and save it in the local directory
	 * @param filename the name of the file to save
	 * @param urlString the url that contains the file
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static void saveUrl(final String filename, final String urlString) throws MalformedURLException, IOException {

		if (filename == null || filename.length() == 0 || urlString == null || urlString.length() == 0)
			throw new IllegalArgumentException("filename and urlString must be true");
		
		BufferedInputStream in = null;
	    FileOutputStream fout = null;
	    try {
	        in = new BufferedInputStream(new URL(urlString).openStream());
	        fout = new FileOutputStream(filename);

	        final byte data[] = new byte[1024];
	        
	        int counter = 0;
	        System.out.print("Waiting");
	        
	        int count;
	        while ((count = in.read(data, 0, 1024)) != -1) { // lettura a buffer
	            fout.write(data, 0, count);
	            if (counter % 15 == 0)
	            	System.out.print(".");
	            counter++;
	        }
	        System.out.println();
	    } finally {
	        if (in != null) in.close();
	        if (fout != null) fout.close();
	    }
	}
	
}
