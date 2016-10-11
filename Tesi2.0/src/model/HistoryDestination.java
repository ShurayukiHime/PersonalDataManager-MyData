package model;

public class HistoryDestination implements IDestination {
	
	private Position position;
	private double probability;
	private String type;
	
	public HistoryDestination (Position position, double probability, String type) {
		this.position = position;
		this.probability = probability;
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	@Override
	public String toDestination() {
		return position.toAddress() + '\n' + "probability: " + probability;
	}
	@Override
	public Position getPosition() {
		return this.position;
	}

}
