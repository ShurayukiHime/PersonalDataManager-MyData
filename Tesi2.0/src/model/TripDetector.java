package model;

import java.time.LocalDateTime;

import persistence.*;

public class TripDetector extends Thread {
	
	private Position oldPosition;// posizione usata per confrontarla con una nuova posizione, al fine di determinare uno spostamento
	private IGpsSimulator gps; 				// simulatore di gps che mi fornisce la posizione
	private boolean travelling; 			// usata per determinare il mio stato [true = sto viaggiando, false = sono fermo (o quasi)]
	private Position from; 		// posizione di partenza del viaggio che salvero'
	private Position to; 			// posizione di destinazione del viaggio che salvero'
	private LocalDateTime date; 			// data di partenza del viaggio che salvero'
	private IPersonalDataVault dataVault;
	
	public TripDetector(IGpsSimulator gps, String username) {
		if (gps == null)
			throw new IllegalArgumentException("gps must be initialized");
		if (username == null || username.length() == 0)
			throw new IllegalArgumentException("username must be initialized");
		this.gps = gps;
		this.travelling = false;
		this.oldPosition = gps.getPosition();
		dataVault = MyData.getInstance().getDataVault(username);
		this.start();
	}

	public Position getPosition() {
		return this.oldPosition;
	}
	
	@Override
	public void start() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Position position = gps.getPosition(); 	// prelevo la posizione, che potrebbe essere cambiata
			System.out.println("TRIP_DETECTOR: travelling="+travelling); // debug
			
			if (position.equalsWithTolerance(oldPosition, 0.0001)) { // sono rimasto fermo // home size tolerance
				if (travelling) { 						// se stavo viaggiando
					travelling = false; 					// "ufficializzo" la sosta
					this.to = position; 					// salvo la destinazione
					ITrip trip = new Trip(from, to, date); 	// creo l'oggetto relativo al viaggio appena terminato
					dataVault.addTrip(trip);				// provo ad aggiungere il viaggio
				}
			} else {
				oldPosition = position; 			// aggiorno la posizione
				if (!travelling) { 					// se non stavo viaggiando
					travelling = true; 					// ufficializzo il mio stato
					this.from = gps.getPosition(); 		// salvo la posizione di partenza
					this.date = LocalDateTime.now(); 	// salvo la data di partenza
				}
			}
		}
	}
}
