package program;

import model.*;

public class GpsSimulatorTest {
	
	public static void main (String args[]) {
		TripDetector gps = new TripDetector(new GpsSimulator(), "prova");
		try {
			gps.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
