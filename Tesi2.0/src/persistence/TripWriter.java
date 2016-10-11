package persistence;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.*;

public class TripWriter {
	
	/*	questa classe e' usata SOLO per
	 *	generare viaggi relativi
	 *	alle mie abitudini,
	 *	per questo motivo e' fortemente
	 * 	cablata e non scritta nel migliore dei modi
	 */
	
	private PrintWriter pw;
	private List<ITrip> trips;
	private Random r;

	public TripWriter() throws FileNotFoundException {
		pw = new PrintWriter("MyTrips.txt");
		trips = new ArrayList<ITrip>();
		r = new Random();
	}

	public List<ITrip> getTrips() {
		return this.trips;
	}

	private void generateLessonTrips() {
		LocalDateTime d = LocalDateTime.now();
		LocalDate ld;
		LocalTime lt;
		Boolean weekendRimini = false;
		int rand;
		d = d.minusDays(100);

		while (d.compareTo(LocalDateTime.now()) < 0) {
			d = d.plusDays(1);
			rand = r.nextInt(100);
			ld = d.toLocalDate();
			lt = d.toLocalTime();

			// lunedi con viaggi casa-coop-facolta
			if (d.getDayOfWeek().equals(DayOfWeek.MONDAY)) { 
				weekendRimini = false;
				if (rand <= 70) {
					lt = LocalTime.of(11 + r.nextInt(4), r.nextInt(59));
					d = LocalDateTime.of(ld, lt);
					trips.add(CasaCoop(d));
					lt = lt.plusMinutes(20 + r.nextInt(10));
					d = LocalDateTime.of(ld, lt);
					trips.add(CoopCasa(d));
				}
				rand = r.nextInt(100);
				if (rand <= 80) {
					lt = LocalTime.of(15, 40 + r.nextInt(15));
					d = LocalDateTime.of(ld, lt);
					trips.add(CasaFacolta(d));
					lt = LocalTime.of(18, 15 + r.nextInt(15));
					d = LocalDateTime.of(ld, lt);
					trips.add(FacoltaCasa(d));
				} else {
					lt = LocalTime.of(15, 40 + r.nextInt(15));
					d = LocalDateTime.of(ld, lt);
					trips.add(casaAllenamento(d));
					lt = LocalTime.of(18, 15 + r.nextInt(15));
					d = LocalDateTime.of(ld, lt);
					trips.add(allenamentoCasa(d));
				}
			}
			// giorni con lezioni 9-19
			else if (d.getDayOfWeek().equals(DayOfWeek.TUESDAY) || d.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)
					|| d.getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
				if (rand <= 80) {
					lt = LocalTime.of(8, 15 + r.nextInt(15));
					d = LocalDateTime.of(ld, lt);
					trips.add(CasaFacolta(d));
					lt = LocalTime.of(18, 35 + r.nextInt(20));
					d = LocalDateTime.of(ld, lt);
					trips.add(FacoltaCasa(d));
				}
			}
			// venerdi si torna a casa e aggiungo gia anche il ritorno la domenica (sono eventi dipendenti)
			else if (d.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
				if (rand < 60) {
					lt = LocalTime.of(14 + r.nextInt(2), r.nextInt(60));
					d = LocalDateTime.of(ld, lt);
					trips.add(BolognaRimini(d));
					ld = ld.plusDays(2);
					lt = LocalTime.of(20, 10 + r.nextInt(20));
					d = LocalDateTime.of(ld, lt);
					trips.add(RiminiBologna(d));
					d = d.minusDays(2);
					weekendRimini = true;
				}
			} 
			// sabato solo sera, probabile cena fuori e forse uscita molto tarda
			else if (d.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
				if (weekendRimini && rand < 80) {
					lt = LocalTime.of(20, r.nextInt(30), 0);
					d = LocalDateTime.of(ld, lt);
					Position to = new Position(44.057910, 12.566142, 2000, new Random()); // posizione casuale nei dintorni
					trips.add(new Trip(new Position(44.057910, 12.566142), to, d));

					rand = r.nextInt(100);
					if (rand < 70) {
						d = d.plusDays(1);
						lt = LocalTime.of(r.nextInt(3), r.nextInt(60), 0);
						d = LocalDateTime.of(ld, lt);
						trips.add(new Trip(to, new Position(44.057910, 12.566142), d));
						// sabato sera random nei dintorni
						d = d.minusDays(1);
					} else {
						lt = LocalTime.of(23, r.nextInt(60));
						d = LocalDateTime.of(ld, lt);
						// rientro a casa
						trips.add(new Trip(to, new Position(44.057910, 12.566142), d));
					}
				}
			} else if (d.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				// decidere se aggiungere qualcosa (considerando che la domenica c'e' gia il viaggio di ritorno a bologna
			}
		}
	}
	
	private void generateSummerTrips() {
		LocalDateTime d = LocalDateTime.now();
		LocalDate ld;
		LocalTime lt;
		int rand;
		d = d.minusDays(100);

		while (d.compareTo(LocalDateTime.now()) < 0) {
			d = d.plusDays(1);
			rand = r.nextInt(100);
			ld = d.toLocalDate();
			lt = d.toLocalTime();

			if (d.getDayOfWeek().equals(DayOfWeek.MONDAY) || d.getDayOfWeek().equals(DayOfWeek.TUESDAY) || d.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)
					|| d.getDayOfWeek().equals(DayOfWeek.THURSDAY) || d.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
				// bassa probabilita di fare qualcosa nel pomeriggio, alta di andare ad allenamento, media di uscire la sera
				
				if (rand <= 25) {
					lt = LocalTime.of(14 + r.nextInt(2), r.nextInt(59));
					d = LocalDateTime.of(ld, lt);
					trips.add(casaMare(d));
					lt = LocalTime.of(17, r.nextInt(30));
					d = LocalDateTime.of(ld, lt);
					trips.add(mareCasa(d));
				}
				rand = r.nextInt(100);
				if (rand <= 90) {
					lt = LocalTime.of(18, r.nextInt(15));
					d = LocalDateTime.of(ld, lt);
					trips.add(casaStadio(d));
					lt = LocalTime.of(20, r.nextInt(30));
					d = LocalDateTime.of(ld, lt);
					trips.add(stadioCasa(d));
				}
				
				rand = r.nextInt(100);
				if (rand <= 50) { // uscita serale random nei dintorni
					lt = LocalTime.of(22, r.nextInt(15));
					d = LocalDateTime.of(ld, lt);
					
					// andata
					Position from = new Position(44.057838, 12.566014);
					double newX = 44.057838 + ((double) r.nextInt(1)/10);
					double newY = 12.566014 + ((double) r.nextInt(1)/10);
					Position to = new Position(newX, newY);
					trips.add(new Trip(from, to, d));
					
					// ritorno
					lt = LocalTime.of(0 + r.nextInt(3), r.nextInt(60));
					ld.plusDays(1);
					d = LocalDateTime.of(ld, lt);
					trips.add(new Trip(to,from,d));
				}
				
			} else if (d.getDayOfWeek().equals(DayOfWeek.SATURDAY) || d.getDayOfWeek().equals(DayOfWeek.SUNDAY)) { // sabato
				// weekend, mare molto probabile il pomeriggio, no allenamento, sera probabile uscita
				if (rand <= 90) {
					lt = LocalTime.of(14 + r.nextInt(2), r.nextInt(59));
					d = LocalDateTime.of(ld, lt);
					trips.add(casaMare(d));
					lt = LocalTime.of(19, r.nextInt(30));
					d = LocalDateTime.of(ld, lt);
					trips.add(mareCasa(d));
				}
				
				rand = r.nextInt(100);
				if (rand <= 90) { // uscita serale random nei dintorni
					lt = LocalTime.of(21, r.nextInt(30) + 30);
					d = LocalDateTime.of(ld, lt);
					
					// andata
					Position from = new Position(44.057838, 12.566014);
					double newX = 44.057838 + ((double) r.nextInt(1)/10);
					double newY = 12.566014 + ((double) r.nextInt(1)/10);
					Position to = new Position(newX, newY);
					trips.add(new Trip(from, to, d));
					
					// ritorno
					lt = LocalTime.of(0 + r.nextInt(3), r.nextInt(60));
					ld.plusDays(1);
					d = LocalDateTime.of(ld, lt);
					trips.add(new Trip(to,from,d));
				}
			}
		}
	}
	
	private ITrip allenamentoCasa(LocalDateTime d) {
		Position to = new Position(44.490589, 11.315008);
		Position from = new Position(44.507890, 11.371972);
		return new Trip(from, to, d);
	}
	
	private ITrip casaAllenamento(LocalDateTime d) {
		Position from = new Position(44.490589, 11.315008);
		Position to = new Position(44.507890, 11.371972);
		return new Trip(from, to, d);
	}
	
	private ITrip stadioCasa(LocalDateTime d) {
		Position to = new Position(44.057903, 12.566122);
		Position from = new Position(44.052251, 12.576836);
		return new Trip(from, to, d);
	}

	private ITrip casaStadio(LocalDateTime d) {
		Position from = new Position(44.057903, 12.566122);
		Position to = new Position(44.052251, 12.576836);
		return new Trip(from, to, d);
	}

	private ITrip mareCasa(LocalDateTime d) {
		Position to = new Position(44.057903, 12.566122);
		Position from = new Position(44.073378, 12.579395);
		return new Trip(from, to, d);
	}

	private ITrip casaMare(LocalDateTime d) {
		Position from = new Position(44.057903, 12.566122);
		Position to = new Position(44.073378, 12.579395);
		return new Trip(from, to, d);
	}

	public void generateFileSummer() {
		generateSummerTrips();
		for (ITrip t : trips) {
			pw.println(t.toString());
		}
		pw.close();
	}

	public void generateFileLesson() {
		generateLessonTrips();
		for (ITrip t : trips) {
			pw.println(t);
		}
		pw.close();
	}

	private ITrip CasaFacolta(LocalDateTime d) {
		return new Trip(new Position(44.490506, 11.314875, 2, new Random()), new Position(44.487961, 11.328705), d);
	}

	private ITrip FacoltaCasa(LocalDateTime d) {
		return new Trip(new Position(44.487961, 11.328705), new Position(44.490506, 11.314875, 2, new Random()), d);
	}

	private ITrip BolognaRimini(LocalDateTime d) {
		return new Trip(new Position(44.490506, 11.314875, 1000, new Random()), new Position(44.057910, 12.566142), d);
	}

	private ITrip RiminiBologna(LocalDateTime d) {
		return new Trip(new Position(44.057910, 12.566142, 1000, new Random()), new Position(44.490506, 11.314875), d);
	}

	private ITrip CasaCoop(LocalDateTime d) {
		return new Trip(new Position(44.490506, 11.314875, 2, new Random()), new Position(44.493703, 11.312646, 5, new Random()), d);
	}

	private ITrip CoopCasa(LocalDateTime d) {
		return new Trip(new Position(44.493703, 11.312646, 5, new Random()), new Position(44.490506, 11.314875, 2, new Random()), d);
	}
}
