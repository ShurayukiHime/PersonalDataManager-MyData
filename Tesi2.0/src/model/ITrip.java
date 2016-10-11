package model;

import java.time.LocalDateTime;

public interface ITrip {

	Position getFrom();

	Position getTo();

	LocalDateTime getDate();
	
	boolean isSimilarTo(ITrip other);

	boolean isDailyAndSimilarTo(ITrip other);
}
