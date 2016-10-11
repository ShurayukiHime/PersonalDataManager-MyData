package model;

import java.util.Random;

import eu.bitm.NominatimReverseGeocoding.Address;
import eu.bitm.NominatimReverseGeocoding.NominatimReverseGeocodingJAPI;
import model.Coordinate;

public class Position extends Coordinate {

	public Position(double latitude, double longitude) {
		super(latitude, longitude);
	}
	
	/**
	 * WARNING: it might be used ONLY when you want to generate a semi-random position
	 * @param latitude
	 * @param longitude
	 * @param delta is the max value to add to both coordinates
	 */
	public Position(double latitude, double longitude, int delta, Random r) {
		this(latitude + r.nextInt(delta) / 10000.0, longitude + r.nextInt(delta) / 10000.0);
	}

	/**
	 * 
	 * @param position is the one with this is compared
	 * @param reference is a position that is used as a reference distance
	 * @return the similarity between this and position
	 */
	public boolean isSimilarTo(Position position, Position reference) {
		if (position == null || reference == null)
			throw new IllegalArgumentException("position and reference must be initialized");
		return deltaDistance(position, reference) < 0.1;
	}

	private double deltaDistance(Position a, Position b) {
		if (a == null || b == null)
			throw new IllegalArgumentException("a and b must be initialized");
		return this.getDistance(a) / a.getDistance(b);
	}

	/**
	 * 
	 * @param other is the second point
	 * @return the distance in km between the two points of map
	 */
	public double getDistance(Coordinate other) {
		if (other == null)
			throw new IllegalArgumentException("other must be initialized");
		
		 double theta = this.getLon() - other.getLon();
		 double dist = Math.sin(deg2rad(this.getLat())) * Math.sin(deg2rad(other.getLat())) + Math.cos(deg2rad(this.getLat())) * Math.cos(deg2rad(other.getLat())) * Math.cos(deg2rad(theta));
		 dist = Math.acos(dist);
		 dist = rad2deg(dist);
		 dist = dist * 60 * 1.1515;
		 //Kilometers
		 dist = dist * 1.609344;
		 
		 return dist;
	}

	private double rad2deg (double rad) {
		return rad * 180 / Math.PI;
	}
	
	private double deg2rad(double deg) {
		return deg * (Math.PI / 180);
	}
	
	/**
	 * it tests if this is next to other
	 * @param other is the second position
	 * @param tolerance is a value that is used to buld a rectangle
	 * @return if this is in the rectangle
	 */
	public boolean equalsWithTolerance(Position other, double tolerance) {
		if (other == null)
			throw new IllegalArgumentException("other must be initialized");

		boolean deltaLat = Math.abs(this.latitude - other.getLat()) < tolerance;
		boolean deltaLong = Math.abs(this.longitude - other.getLon()) < tolerance;
		return deltaLat && deltaLong;
	}
	
	public String toString() {
		return latitude + ", " + longitude;
	}
	
	/**
	 * this method uses OSM's nominatim apis
	 * @param latitude 
	 * @param longitude
	 * @return a human readable string that represents the address of a given location 
	 */
	public String toAddress() {
		NominatimReverseGeocodingJAPI nominatim = new NominatimReverseGeocodingJAPI();
		Address address = nominatim.getAdress(this.getLat(), this.getLon());
		return address.toString();
	}

//	@Override
//	public String toDestination() {
//		return toAddress();
//	}
//
//	@Override
//	public Position getPosition() {
//		return this;
//	}

}
