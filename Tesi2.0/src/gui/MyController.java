package gui;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import model.*;
import model.MyData.DataSet;
import model.MyData.IDataSet;
import model.MyData.IMyData;
import model.MyData.MyData;
import model.consents.ConsentManager;
import model.consents.ConsentStatus;
import model.consents.ServiceConsent;
import model.mapfeatures.Position;
import model.registry.Metadata;
import model.services.IService;
import model.services.MostLikelyNextTrip;
import model.userdata.IDestination;
import model.userdata.IPreference;
import model.userdata.Preference;
import model.userdata.suggestions.ISuggestion;
import model.userdata.suggestions.SuggesterManager;
import model.users.IAccount;
import model.users.IUser;
import persistence.*;

public class MyController implements Controller {

	private List<IPreference> preferences;
	private Position actualPosition;
	private LocalDateTime date;
	private IMyData myDataInstance;
	private Map<IService, JFrame> mappingServicePanel;
	private IUser authenticatedUser;
	private UserInteractor userInteractor;

	public MyController(UserInteractor userInteractor) {
		preferences = new ArrayList<>();
		this.myDataInstance = MyData.getInstance();
		this.mappingServicePanel = new HashMap<IService, JFrame>();
		this.userInteractor = userInteractor;
	}

	@Override
	public UserInteractor getUserInteractor() {
		return userInteractor;
	}

	public List<IPreference> getUIPreferences() {
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

	public void addUIPreference(String category, String name) {
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

	public void resetUIPreferences() {
		preferences.clear();
	}

	public void fillPreferencesByCategory(String category, List<JCheckBox> checks) {
		if (category == null || category.length() == 0)
			throw new IllegalArgumentException("The field Category must not be null or empty.");
		if (checks == null)
			throw new IllegalArgumentException("Checks must be initialized.");
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

	private void printSuggestions(MainFrame panel, Object result) throws FileNotFoundException, IOException {
		//IMPLEMENTARE TIPO DI RITORNO IN SERVICE REGISTRY + DATACONSENT
		List<ISuggestion> suggestions = (List<ISuggestion>) result;

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

		panel.setSuggestions(suggestionsBuilder.toString());
		panel.setProbability(infoBuilder.toString());
		this.updateMap(panel.getMap(), suggestions);
	}

	@Override
	public void createMyDataUser(String firstName, String lastName, Date dateOfBirth, String emailAddress,
			String password) {
		authenticatedUser = myDataInstance.createMyDataAccount(firstName, lastName, dateOfBirth, emailAddress, password);
	}

	@Override
	public void logInUser(String email, String password) {
		authenticatedUser = myDataInstance.loginUser(email, password);
	}

	@Override
	public void toggleStatus(IService selectedService, boolean status) {
		// l'user è già autenticato, in teoria non dovrei fare controlli su
		// authusr == null. poichè invocato da profile panel,l'account presso il
		// servizio dovrebbe sempre esistere

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
		// retrieve account for service (user SHOULD have it)
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
	public void addService(IService service) {
		// Service Linking should be here
		
		//barbatrucco per mancanza service linking...
		if (service != null) {
			this.myDataInstance.createServiceAccount(authenticatedUser, service);
			//in qualche modo qui si dovrebbe aggiornare la mappa con il jpanel giusto x il servizio aggiunto
		} else {
			IService mlnt = new MostLikelyNextTrip();
			this.myDataInstance.createServiceAccount(authenticatedUser, mlnt);
			this.mappingServicePanel.put(mlnt, new MainFrame(this));
		}
	}

	@Override
	public void withdrawConsentForService(IService selectedService) {
		ConsentManager.changeServiceConsentStatusForService(authenticatedUser, selectedService,
				ConsentStatus.WITHDRAWN);
	}

	@Override
	public List<IService> getAllActiveServicesForUser() {
		List<IService> activeServices = new ArrayList<IService>();
		for (IAccount a : authenticatedUser.getAllAccounts()) 
			if (a.getActiveDisabledSC() != null)
				activeServices.add(a.getService());
		return activeServices;
	}

	@Override
	public boolean getADStatusForService(IService selectedService) {
		for (IAccount a : authenticatedUser.getAllAccounts())
			if (a.getService().equals(selectedService))
				return a.getActiveDisabledSC().getConsentStatus() == ConsentStatus.ACTIVE ? true : false;
		throw new IllegalArgumentException("Current user does not have an Active or Disabled account to the selected service.");
	}

	@Override
	public JFrame getServicePanel(IService selectedService) {
		// selected service è preso dalla gui quindi dovrebbe essere x forza non nullo
		if (!this.mappingServicePanel.containsKey(selectedService))
			throw new IllegalArgumentException("The service " + selectedService.toString() + " should have registered a User Interface!");
		return this.mappingServicePanel.get(selectedService);
	}

	@Override
	public void provideConcreteService(MainFrame mainFrame) throws FileNotFoundException, IOException {
		IService s = this.findService(mainFrame);
		this.printSuggestions(mainFrame, s.provideService(authenticatedUser));
	}

	private IService findService(MainFrame frame) {
		for (IService s : this.mappingServicePanel.keySet()) {
			if (this.mappingServicePanel.get(s).equals(frame))
				return s;
		}
		throw new IllegalArgumentException("Could not find registered Service for given MainFrame.");
	}
	
	@Override
	public void updateModel(MainFrame mainFrame) {
		IDataSet dataSet = new DataSet();
		
		dataSet.put(Metadata.PREFERENCE_CONST, this.preferences);
		dataSet.put(Metadata.DATE_CONST, this.date);
		dataSet.put(Metadata.POSITION_CONST, this.actualPosition);
		
		IService s = this.findService(mainFrame);
		s.gatherData(authenticatedUser, dataSet);
	}

	@Override
	public void addNewServiceConsent(IService selectedService) {
		this.myDataInstance.issueNewServiceConsent(selectedService, authenticatedUser);
	}
}
