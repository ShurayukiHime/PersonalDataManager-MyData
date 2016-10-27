package gui;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import model.*;
import persistence.*;
import persistence.consents.ConsentManager;
import persistence.consents.ConsentStatus;
import persistence.consents.ServiceConsent;
import persistence.users.IAccount;
import persistence.users.IUser;

public class MyController implements Controller {

	private List<IPreference> preferences;
	private Position actualPosition;
	private LocalDateTime date;
	private IMyData myDataInstance;
	private IUser authenticatedUser;

	public MyController() {
		preferences = new ArrayList<>();
		this.myDataInstance = MyData.getInstance();
	}

	public List<IPreference> getPreferences() {
		return this.preferences;
	}

	public void setActualPosition(double latitude, double longitude) {
		if (latitude > 90 || latitude < -90)
			throw new IllegalArgumentException("latitude must be between -90 and 90");
		if (longitude > 180 || longitude < -180)
			throw new IllegalArgumentException("longitude must be between -180 and 180");
		Position position = new Position(latitude, longitude);
		actualPosition = position;
	}

	public Position getActualPosition() {
		return this.actualPosition;
	}

	public void addPreference(String category, String name) {
		preferences.add(new Preference(category, name));
	}

	public void setPanelDate(JTextField day, JTextField month, JTextField year, JTextField hour, JTextField min) {
		LocalDateTime ldt = LocalDateTime.now();
		day.setText(ldt.getDayOfMonth() + "");
		month.setText(ldt.getMonthValue() + "");
		year.setText(ldt.getYear() + "");
		hour.setText(ldt.getHour() + "");
		min.setText(ldt.getMinute() + "");
	}

	public void setDate(JTextField day, JTextField month, JTextField year, JTextField hour, JTextField min) {
		int y = Integer.parseInt(year.getText());
		int d = Integer.parseInt(day.getText());
		int m = Integer.parseInt(month.getText());
		int h = Integer.parseInt(hour.getText());
		int mm = Integer.parseInt(min.getText());
		this.date = LocalDateTime.of(y, m, d, h, mm);
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public void resetPreferences() {
		preferences.clear();
	}

	public void fillPreferencesByCategory(String category, List<JCheckBox> checks) {
		if (category == null || category.length() == 0)
			throw new IllegalArgumentException("category must be true");
		if (checks == null)
			throw new IllegalArgumentException("checks must be initialized");
		for (JCheckBox cb : checks) {
			if (cb.isSelected())
				preferences.add(new Preference(category, cb.getText()));
		}
	}

	private void updateMap(JMapViewer map, List<ISuggestion> suggestions) {
		boolean first = true;
		map.getMapMarkerList().clear();
		for (ISuggestion s : suggestions) {
			for (IDestination d : s.getDestinations()) {
				MapMarkerDot mmd = new MapMarkerDot(d.getPosition().getLat(), d.getPosition().getLon());
				if (first) {
					mmd.setColor(Color.red);
					first = false;
				}
				map.addMapMarker(mmd);
			}
		}
		map.setDisplayToFitMapMarkers();
	}

	public void getSuggest(MainFrame panel) throws FileNotFoundException, IOException {
		if (panel == null)
			throw new IllegalArgumentException("panel must be initialized");

		// TODO

		// da rifattorizzare tutto in qualche modo, prevedendo in ordine

		// creazione di account MyData
		// creazione di account presso il servizio
		// firma di un service consent per il servizio di previsione di viaggio
		// firma di uno+ data consent per l'utilizzo effettivo del servizio

		MyData.getInstance().getDataVault(username).getPreferences().clear();
		MyData.getInstance().getDataVault(username).getPreferences().addAll(preferences);
		List<ISuggestion> suggestions = SuggesterManager.getInstance().getSuggestions(date, actualPosition,
				MyData.getInstance().getDataVault(username));

		StringBuilder suggestionsBuilder = new StringBuilder();
		StringBuilder infoBuilder = new StringBuilder();

		for (ISuggestion s : suggestions) {
			for (IDestination d : s.getDestinations()) {
				if (d != null) {
					suggestionsBuilder.append(d.toDestination());
					suggestionsBuilder.append('\n');
				}
			}
			suggestionsBuilder.append('\n');
			infoBuilder.append(s.getInfo());
			infoBuilder.append('\n');
			infoBuilder.append('\n');
		}

		panel.getSuggestions().setText(suggestionsBuilder.toString());
		panel.getProbability().setText(infoBuilder.toString());
		updateMap(panel.getMap(), suggestions);
	}

	@Override
	public void createMyDataUser(String firstName, String lastName, Date dateOfBirth, String emailAddress,
			String password) {
		myDataInstance.createMyDataAccount(firstName, lastName, dateOfBirth, emailAddress, password);
	}

	@Override
	public void logInUser(String email, String password) {
		authenticatedUser = myDataInstance.loginUser(email, password);
	}

	@Override
	public void toggleStatus(IService selectedService, boolean status) {
		// get current status for service
		// invoke method to switch status

		// poichè questa funzione viene chiamata solo da profile panel, quando
		// l'user è già autenticato, in teoria non dovrei fare controlli su
		// authusr == null
		ServiceConsent consent = null;
		for (IAccount a : authenticatedUser.getAllAccounts()) {
			if (a.getService().equals(selectedService))
				consent = a.getActiveDisabledSC();
		}

		if (status) {
			ConsentManager.changeServiceConsentStatusForService(authenticatedUser, selectedService,
					ConsentStatus.ACTIVE);
		} else {
			ConsentManager.changeServiceConsentStatusForService(authenticatedUser, selectedService,
					ConsentStatus.DISABLED);
		}
	}

	@Override
	public String getAllPastSConsents(IService selectedService) {
		//retrieve account for service (user SHOULD have it)
		// get all consents
		// for each sconsent, toString();
		
		StringBuilder sb = new StringBuilder();
		IAccount accountAtService = null;
		for (IAccount a : authenticatedUser.getAllAccounts()) {
			if (a.getService().equals(selectedService))
				accountAtService = a;
		}
		for (ServiceConsent sc : accountAtService.getAllPastServiceConsents()) 
			sb.append(sc.toString() + System.getProperty("line.separator"));
		return sb.toString();
	}

	@Override
	public void addService() {
		// TODO Auto-generated method stub
		//should add MLNT
	}

}
