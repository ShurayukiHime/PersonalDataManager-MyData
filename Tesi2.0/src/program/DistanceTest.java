package program;

import model.*;

public class DistanceTest {
	
	public static void main (String args[]) {
		Position p1 = new Position(38.474833, -101.467000);
		Position p2 = new Position(56.941795, 48.034945);
		double res = getDistanza(p1, p2);
		System.out.println(res);
	}
	
	public static double getDistance(Position tis, Position other) {
		double R = 6371; // Radius of the earth in km
		 // calcolo della distanza secondo la formula di Haversine
		double dLat = deg2rad(other.getLat() - tis.getLat());
		double dLon = deg2rad(other.getLon() - tis.getLon());
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(tis.getLat()))
				* Math.cos(deg2rad(other.getLon())) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c; // Distance in km
		return d;
	}
	
	public static double getDistanza(Position p1, Position p2) {
		 double theta = p1.getLon() - p2.getLon();
		 double dist = Math.sin(deg2rad(p1.getLat())) * Math.sin(deg2rad(p2.getLat())) + Math.cos(deg2rad(p1.getLat())) * Math.cos(deg2rad(p2.getLat())) * Math.cos(deg2rad(theta));
		 dist = Math.acos(dist);
		 dist = rad2deg(dist);
		 dist = dist * 60 * 1.1515;
		 //Kilometers
		 dist = dist * 1.609344;
		 
		 return dist;
	}
	
	private static double rad2deg (double rad) {
		return rad * 180 / Math.PI;
	}

	private static double deg2rad(double deg) {
		return deg * (Math.PI / 180);
	}
	
}
