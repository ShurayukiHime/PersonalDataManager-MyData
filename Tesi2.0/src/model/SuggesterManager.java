package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.pavie.basicosmparser.controller.OSMParser;
import info.pavie.basicosmparser.model.Element;
import info.pavie.basicosmparser.model.Node;
import info.pavie.basicosmparser.model.Relation;
import info.pavie.basicosmparser.model.Way;
import persistence.*;

public class SuggesterManager {

	private IPersonalDataVault dataVault;
	private static SuggesterManager instance;
		
	private SuggesterManager() {
	}
	
	public static SuggesterManager getInstance() {
		if (instance == null)
			instance = new SuggesterManager();
		return instance;
	}
	
	private class HistorySuggester implements ISuggester {
		
		private LocalDateTime date;
		private Position position;
		private List<ITrip> allTrips;
		
		private final int DAYS_TO_FILTER = 30; // un mese di abitudini pseudo-giornaliere
		private final int DAYS_TO_FILTER_DAILY = 70; // almeno 10 giorni di un'abitudine compiuta 1 volta a settimana

		
		public HistorySuggester(LocalDateTime date, Position position, List<ITrip> allTrips) {
			if (date == null || position == null || allTrips == null)
				throw new IllegalArgumentException("date, position and allTrip must be initialized");
			this.position = position;
			this.date = date;
			this.allTrips = allTrips;
		}

		/**
		 * @return a list of trips with the same start position as specified in "actualPosition"
		 * @throws FileNotFoundException
		 * @throws IOException
		 */
		private List<ITrip> getFromMatches(List<ITrip> trips) throws IOException {
			if (trips == null)
				throw new IllegalArgumentException("trips must be initialized");
			List<ITrip> result = new ArrayList<ITrip>();

			for (ITrip trip : trips) {
				if (position.isSimilarTo(trip.getFrom(), trip.getTo()))
					result.add(trip);
			}

			return result;
		}
		
		/**
		 * @param trips
		 *            : a list of ITrip to filter
		 * @return all trips that have a leaving time similar (with 30mins of
		 *         tolerance) to the actual time
		 */
		private List<ITrip> filterByTime(List<ITrip> trips) {
			if (trips == null)
				throw new IllegalArgumentException("trips must be initialized");
			List<ITrip> result = new ArrayList<ITrip>();
			for (ITrip t : trips) {
				if (Utils.deltaTimeMinute(date, t.getDate()) < 30 && // se l'orario e' simile
						t.getDate().isBefore(this.date) && // se la data del viaggio e' contenuta tra la data passata (teoricamente oggi)
						t.getDate().plusDays(DAYS_TO_FILTER).isAfter(this.date)) { // e DAYS_TO_FILTER giorni prima

					result.add(t);
				}
			}
			return result;
		}
		
		/**
		 * @param trips
		 *            : a list of ITrip to filter
		 * @return all trips that have a leaving time similar (with 30mins of
		 *         tolerance) to the actual time and in the same dayOfWeek
		 */
		private List<ITrip> filterByTimeDaily(List<ITrip> trips) {
			if (trips == null)
				throw new IllegalArgumentException("trips must be initialized");
			List<ITrip> result = new ArrayList<ITrip>();

			for (ITrip t : trips) {
				if (Utils.deltaTimeMinute(date, t.getDate()) < 60 && // se l'orario e' simile
						t.getDate().isBefore(this.date) && // se la data del viaggio e' contenuta tra la data passata (teoricamente oggi)
						t.getDate().plusDays(DAYS_TO_FILTER_DAILY).isAfter(this.date) && // e DAYS_TO_FILTER giorni prima
						t.getDate().getDayOfWeek().equals(date.getDayOfWeek())) {

					result.add(t);
				}
			}

			return result;
		}

		/**
		 * This method makes a map of destinations and trip details to decide which
		 * is the most probable destination. This method also set the probability
		 * value of this class.
		 * 
		 * @param trips
		 * @return the most likely destination
		 */
		private HistorySuggestion getHistorySuggestion(List<ITrip> trips, boolean daily) {
			if (trips == null)
				throw new IllegalArgumentException("trips must be initialized");
			Map<Position, TripDetails> destCount = new HashMap<Position, TripDetails>();
			HistorySuggestion result = new HistorySuggestion();
//			Position destination = null;
//			double probability = 0;

			int daysToFilter = daily ? DAYS_TO_FILTER_DAILY : DAYS_TO_FILTER;

			for (ITrip t : trips) { // per ogni viaggio passato
				boolean found = false;
				for (Position p : destCount.keySet()) { // per ogni possibile destinazione della mappa (inizialmente vuota)
					if (p.isSimilarTo(t.getTo(), t.getFrom())) { // se la destinazione e' simile alla dest del viaggio (rispetto alla partenza)
						destCount.get(p).increaseValue(date, t.getDate(), daysToFilter); // allora incremento il conteggio relativo alla destinazione p
						destCount.get(p).addDay(t.getDate().getDayOfWeek()); // ai giorni in cui quel viaggio e' stato fatto aggiungo quello corrente
						found = true;
						break; // una volta trovato esco, efficienza
					}
				}
				if (!found) {
					destCount.put(t.getTo(), new TripDetails()); // se non viene trovata una dest simila, la aggiungo alla mappa
					destCount.get(t.getTo()).addDay(t.getDate().getDayOfWeek()); // inizializzo i dettagli e aggiungo il giorno corrente
					destCount.get(t.getTo()).increaseValue(date, t.getDate(), daysToFilter); // valuto il valore del giorno del viaggio e lo inserisco
				}
			} // a fine ciclo ho costruito la mappa con tutte le possibili destinazioni, ognuna associata a un insieme di dettagli

//			double max = 0;
//			for (Position p : destCount.keySet()) { // per ogni destinazione possibile
//				if (destCount.get(p).getValue() > max && // se la destinazione corrente e' stata effettuata piu di max volte
//						destCount.get(p).getDaysOfWeek().contains(this.date.getDayOfWeek())) { // e la dest e' stata raggiunta in un dayOfWeek come quello attuale
//					max = destCount.get(p).getValue(); // aggiorno il max
//					destination = p; // setto il risultato alla destinazione corrente
//				}
//			}
//			if (destination != null)
//				probability = calculateProbability(destCount.get(destination), daily); // setto la probabilita
			for (Position p : destCount.keySet()) {
				if (destCount.get(p).getDaysOfWeek().contains(date.getDayOfWeek()))
					result.getDestinations().add(new HistoryDestination(p, calculateProbability(destCount.get(p), daily, p), daily ? "daily" : "1toW"));
			}
			return result;
		}

		/**
		 * This method calculates the probability of the suggested trip
		 * 
		 * @param td
		 *            is the object that contains details about the suggested
		 *            destination
		 * @return the probability of the destination
		 */
		private double calculateProbability(TripDetails td, boolean daily, Position reference) {
			if (td == null)
				throw new IllegalArgumentException("td and ref must be intialized");
			LocalDateTime tempDate = this.date;
			double totalPossibleDays = 0;

			int days = daily ? DAYS_TO_FILTER_DAILY : DAYS_TO_FILTER;

			for (int i = 0; i < days; i++) {
				if (td.getDaysOfWeek().contains(tempDate.minusDays(i).getDayOfWeek())) { // calcolo in quanti giorni il viaggio in questione
//					if (dataVault.getPositionByDate(tempDate.minusDays(i)).isSimilarTo(position, reference)) // potrebbe essere stato fatto
						totalPossibleDays += Utils.dayValue(i, days);
				}
			}
			double result = (double) td.getValue() * 100 / (double) totalPossibleDays; // giorni ion cui l'ho fatto / i giorni in cui lo avrei potuto fare
			return result;
		}

		
		@Override
		public List<ISuggestion> getSuggestions() throws FileNotFoundException, IOException {
			List<ISuggestion> result = new ArrayList<ISuggestion>();
			HistorySuggestion hs1 = getHistorySuggestion(filterByTime(getFromMatches(allTrips)), false);
			HistorySuggestion hs2 = getHistorySuggestion(filterByTimeDaily(getFromMatches(allTrips)), true);

			result.add(hs1);
			result.add(hs2);
			
//			if (hs1.getPosition() != null || hs2.getPosition() != null)
//				result.add(hs1.getProbability() > hs2.getProbability() ? hs1 : hs2);
			
			return result;
		}

	}

	private class CalendarSuggester implements ISuggester {
		
		private LocalDateTime date;
		
		public CalendarSuggester(LocalDateTime date) {
			if (date == null)
				throw new IllegalArgumentException("date must be initialized");
			this.date = date;
		}

		@Override
		public List<ISuggestion> getSuggestions() throws FileNotFoundException, IOException {
			List<ISuggestion> result = new ArrayList<>();
			ICalendar calendar = dataVault.getCalendar();
			List<AbstractCommitment> commitments = calendar.getCommitmentByDate(date);
			if (!commitments.isEmpty()) {
				result.add(new CalendarSuggestion(commitments));
			}
			return result;
		}
		
	}
	
	private class PreferenceSuggester implements ISuggester {
		
		private List<IPreference> preferences;
		private List<Position> positions;
		
		public PreferenceSuggester(List<IPreference> preferences, List<Position> positions) {
			if (preferences == null || positions == null)
				throw new IllegalArgumentException("preferences and positions must be initialized");
			this.preferences = preferences;
			this.positions = positions;
		}
		
		/**
		 * 
		 * @param position is the center of the area which is analysed to find near features
		 * @return a list of all features found that match at least one of the preferences
		 */
		private List<AbstractFeature> getFeatures(Position position) {
			if (position == null)
				throw new IllegalArgumentException("position must be initialized");
			List<AbstractFeature> result = new ArrayList<>();
			
			String fileName = "map.osm";
			String urlString = "http://api.openstreetmap.org/api/0.6/map?bbox=";

			double delta = 0.002; // compromesso medio per i test

			double minlat = position.getLat() - delta; // definisco un'area rettangolare nei ditorni della positione passata
			double minlong = position.getLon() - delta;
			double maxlat = position.getLat() + delta;
			double maxlong = position.getLon() + delta;

			System.out.println("starting download");

			// mi connetto all'url per scaricare la porzione di mappa desiderata
			URL url = null;
			try {
				url = new URL(urlString + minlong + "," + minlat + "," + maxlong + "," + maxlat + "");
				System.out.println(url);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			// scarico e salvo il file xml contenente i dati della mappa
			try {
				Utils.saveUrl(fileName, url.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// debug
			System.out.println("download finished");
			System.out.println("computing results");

			
			OSMParser parser = new OSMParser();
			File file = new File(fileName);
			Map<String, Element> map = null;
			try {
				map = parser.parse(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (String key : map.keySet()) {
				
				Element element = map.get(key);
				Map<String, String> tags = element.getTags();
				boolean match = false;
				for (String k : tags.keySet()) { // verifica che tra i tag dell'entity ce ne sia almeno uno che faccia match con le
					for (IPreference p : preferences) { // preferenze specificate
						if (((p.getCategory().equals("sport") || p.getCategory().equals("historic")) && p.getCategory().equals(k)) || (p.getName() != null && p.getName().equals(tags.get(k)))) {
							match = true;
							break;
						}
					}
				}
				
				if (match) { // se l'entity ha fatto match con almeno una preferenza
					AbstractFeature feature = new Feature();
					for (String k : tags.keySet()) { // aggiungo alla feature tutti i tag che fanno match con quelli delle preferenze (evito di mettere quelli inutili)
						for (IPreference p : preferences) {
							if (p.getCategory().equals("sport") || p.getCategory().equals("historic")) { // historic e sport sono key, ma per motivi di logica non vengono considerati i value
								if (p.getCategory().equals(k)) {
									feature.insertTag(k, tags.get(k));
								}
							} else if (p.getName().equals(tags.get(k)) || k.equals("name") || k.equals("website")) { // vengono considerati anche name e website poiche tag "utili/interessanti", ma a livello concettuale non esprimibili da preferenze
								feature.insertTag(k, tags.get(k));
							} // gli if devono rimanere separati per problemi di NullPointerException (e cosi' rimane comunque piu leggibile)
						}
					}

					feature.setPosition(getFeaturePositionById(element.getId(), map));
					
					if (feature.getTags().size() > 0 || feature.getName().length() > 0) {
						result.add(feature);
						System.out.println("one feature added");
					}
				}
			}
			System.out.println("all features added");
			return result;
		}
		
		/**
		 * 
		 * @param id the id of the element that you have to find the location
		 * @param map the content of the donwloaded file
		 * @return the position of an element with the given id, in the given map
		 */
		private Position getFeaturePositionById(String id, Map<String,Element> map) {
			if (id == null || id.length() == 0)
				throw new IllegalArgumentException("id must be true");
			if (map == null)
				throw new IllegalArgumentException("map must be initialized");
			Node node = null;
			
			if (id.startsWith("W")) {
				Way way = (Way) map.get(id);
				node = way.getNodes().get(0);

			} else if (id.startsWith("N")) {
				node = (Node) map.get(id);
			} else if (id.startsWith("R")) {
				Relation relation = (Relation) map.get(id);
				List<Element> members = relation.getMembers();
				Element element = null;
				for (Element e : members) {
					if (map.get(e.getId()) != null) {
						element = e;
						break;
					}
				}
				return getFeaturePositionById(element.getId(), map);
			}		
			return new Position(node.getLat(), node.getLon());
		}

		@Override
		public List<ISuggestion> getSuggestions() throws FileNotFoundException, IOException {
			List<ISuggestion> suggestions = new ArrayList<>();
			for (Position position : positions) {
				List<AbstractFeature> features = getFeatures(position);
				suggestions.add(new PreferenceSuggestion(preferences, features));
			}
			return suggestions;
		}
		
	}
	
	
	private void addPositions(List<ISuggestion> suggestions, List<Position> positions) {
		for (ISuggestion suggestion : suggestions) {
			for (IDestination destination : suggestion.getDestinations()) {
				positions.add(destination.getPosition());
			}
		}
	}

	/**
	 * 
	 * @param date is the temporal coordinate and represents the "today"
	 * @param actualPosition is the space coordinate
	 * @param pdt is the personal data vault of the user
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<ISuggestion> getSuggestions(LocalDateTime date, Position actualPosition, IPersonalDataVault pdt) throws FileNotFoundException, IOException {
		if (date == null || actualPosition == null || pdt == null)
			throw new IllegalArgumentException("date, actualPosition and pdt must be initialized");
		this.dataVault = pdt;
		
		List<ISuggestion> result = new ArrayList<>();
		List<Position> positions = new ArrayList<>();
		List<IPreference> preferences = dataVault.getPreferences();

		CalendarSuggester cs = new CalendarSuggester(date);
		result.addAll(cs.getSuggestions());

		if (result.isEmpty()) {
			HistorySuggester hs = new HistorySuggester(date, actualPosition, dataVault.getAllTrip());
			result.addAll(hs.getSuggestions());
		}
		
		addPositions(result, positions);
		positions.add(actualPosition);
		if (!preferences.isEmpty()) {
			PreferenceSuggester ps = new PreferenceSuggester(preferences, positions);
			result.addAll(ps.getSuggestions());
		}
		return result; // restituisco il risultato piu probabile
	}

}
