package model;

import java.time.LocalDateTime;

public class Trip implements ITrip {
	
	private final int DELTA_TIME = 15;

	private Position from, to;
	private LocalDateTime date;
	
	public Trip(Position from, Position to, LocalDateTime date) {
		if (from == null || to == null || date == null)
			throw new IllegalArgumentException("from, to and date must be initialized");
		this.from = from;
		this.to = to;
		this.date = date;
	}

	@Override
	public Position getFrom() {
		return from;
	}


	@Override
	public Position getTo() {
		return to;
	}

	@Override
	public LocalDateTime getDate() {
		return date;
	}

	@Override
	public boolean isSimilarTo(ITrip other) {
		if (other == null)
			throw new IllegalArgumentException("other must be initialized");
		boolean from = this.getFrom().isSimilarTo(other.getFrom(), other.getTo());
		boolean to = this.getTo().isSimilarTo(other.getTo(), other.getFrom());
		boolean date = Utils.deltaTimeMinute(this.getDate(), other.getDate()) < DELTA_TIME;
		
		return from && to && date;
	}
	
	@Override
	public boolean isDailyAndSimilarTo(ITrip other) {
		if (other == null)
			throw new IllegalArgumentException("other must be initialized");
		return isSimilarTo(other) && this.getDate().getDayOfWeek().equals(other.getDate().getDayOfWeek());
	}
	
	@Override
	public String toString () {
		String result = "";
		
		result += from + "," + to + ",";
		result += date.getYear() + "," + date.getMonth() + "," + date.getDayOfMonth() + "," + date.getHour() + "," + date.getMinute();
		
		return result;
	}

}
