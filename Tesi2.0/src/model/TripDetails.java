package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TripDetails {
	private double value; // il valore associato ad una destinazione
	private List<DayOfWeek> daysOfWeek; // lista di giorni della settimana in cui la destinazione viene raggiunta
	
	/**
	 * questa classe e' utile per determinare la destinazione piu probabile e la relativa probabilita 
	 * i calcoli sono basati su: 
	 * VALUE, indica il numero di volte che il viaggio viene fatto pesato in base alla distanza dal giorno attuale
	 * DAYS_OF_WEEK, indica i giorni della settimana in cui il viaggio viene fatto, viene usato sia per i viaggi quotidiani che settimanali
	 */
	public TripDetails () {
		this.value = 0;
		this.daysOfWeek = new ArrayList<>();
	}
	
	public double getValue() {
		return this.value;
	}
	
	
	/**
	 * this method add a quantity to "value", taking into consideration the distance of date from today, if they are closer, the quantity to add will be higher
	 * @param today is the today date
	 * @param date is the date of the past trip with a possible destination
	 * @param days is the number of day to filter, it could be 30 or 70 and depends on the type of the analyzed trip
	 */
	public void increaseValue (LocalDateTime today, LocalDateTime date, int days) {
		this.value += Utils.dayValue(today, date, days);
	}
	
	public void addDay (DayOfWeek day) {
		if (day == null)
			throw new IllegalArgumentException("day must be initialized");
		this.daysOfWeek.add(day);
	}
	
	public List<DayOfWeek> getDaysOfWeek() {
		return this.daysOfWeek;
	}
	
}
