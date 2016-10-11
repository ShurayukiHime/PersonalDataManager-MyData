package model;

import java.time.LocalDateTime;

public class Commitment extends AbstractCommitment {
	
	private String description;
	private LocalDateTime date;
	private Position position;
	
	public Commitment(String description, LocalDateTime date, Position position) {
		if (description == null || date == null || position == null)
			throw new IllegalArgumentException("commitment constructor failed");
		this.description = description;
		this.date = date;
		this.position = position;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public LocalDateTime getDate() {
		return date;
	}

	@Override
	public Position getPosition() {
		return position;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(position.toAddress());
		sb.append('\n');
		sb.append(date);
		sb.append('\n');
		sb.append(description);
		sb.append('\n');
		
		return sb.toString();
	}

	@Override
	public String toDestination() {
		return this.toString();
	}

}
