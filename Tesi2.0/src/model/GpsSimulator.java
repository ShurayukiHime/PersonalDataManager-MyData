package model;

import java.util.Random;

public class GpsSimulator implements IGpsSimulator {

	private Position position;
	private Random random;
	
	public GpsSimulator() {
		this(new Position(44, 12));
	}
	
	public GpsSimulator (Position position) {
		if (position == null)
			throw new IllegalArgumentException("position must be initialized");
		this.position = position;
		this.random = new Random();
	}
	
	@Override
	public Position getPosition() { // in questa implementazione mi serve che la get restituisca a volte valori diversi, altre valori uguali
		int r = random.nextInt(100);
		if (r < 90) {
			this.position = new Position(position.getLat() + 0.002, position.getLon() + 0.002);
			System.out.println("GPS: posizione cambiata");
		}
		return this.position;
	}

}
