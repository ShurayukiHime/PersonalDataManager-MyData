package model;

import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

public class Coordinate implements ICoordinate {
	protected double latitude, longitude;

	public Coordinate(double latitude, double longitude) {
		if (latitude > 90 || latitude < -90)
			throw new IllegalArgumentException("latitude must be between -90 and 90");
		if (longitude > 180 || longitude < -180)
			throw new IllegalArgumentException("longitude must be between -180 and 180");
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLat() {
		return latitude;
	}

	public void setLat(double latitude) {
		if (latitude > 90 || latitude < -90)
			throw new IllegalArgumentException("latitude must be between -90 and 90");
		this.latitude = latitude;
	}

	public double getLon() {
		return longitude;
	}

	public void setLon(double longitude) {
		if (longitude > 180 || longitude < -180)
			throw new IllegalArgumentException("longitude must be between -180 and 180");
		this.longitude = longitude;
	}
	
	public String toString() {
		return latitude + ", " + longitude;
	}

}
