package gui;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import model.*;
import persistence.*;

public class Controller {
	
	private List<IPreference> preferences;
	private Position actualPosition;
	private LocalDateTime date;
	private String username;
	
	public Controller(String username) {
		if (username == null || username.length() == 0)
			throw new IllegalArgumentException("username must be true");
		preferences = new ArrayList<>();
		this.username = username;
	}
	
	public List<IPreference> getPreferences() {
		return this.preferences;
	}
	
	public void setActualPosition(double latitude, double longitude) {
		if (latitude > 90 || latitude < -90)
			throw new IllegalArgumentException("latitude must be between -90 and 90");
		if (longitude > 180 || longitude < -180)
			throw new IllegalArgumentException("longitude must be between -180 and 180");
		Position position = new Position(latitude,longitude);
		actualPosition = position;
	}
	
	public Position getActualPosition() {
		return this.actualPosition;
	}
	
	public void addPreference(String category, String name) {
		preferences.add(new Preference(category, name));
	}
	
	public void setPanelDate (JTextField day, JTextField month, JTextField year, JTextField hour, JTextField min) {
		LocalDateTime ldt = LocalDateTime.now();
		day.setText(ldt.getDayOfMonth() + "");
		month.setText(ldt.getMonthValue() + "");
		year.setText(ldt.getYear() + "");
		hour.setText(ldt.getHour() + "");
		min.setText(ldt.getMinute() + "");
	}
	
	public void setDate (JTextField day, JTextField month, JTextField year, JTextField hour, JTextField min) {
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
	
	public void getSuggest (MainFrame panel) throws FileNotFoundException, IOException {
		if (panel == null)
			throw new IllegalArgumentException("panel must be initialized");
		MyData.getInstance().getDataVault(username).getPreferences().clear();
		MyData.getInstance().getDataVault(username).getPreferences().addAll(preferences);
		List<ISuggestion> suggestions = SuggesterManager.getInstance().getSuggestions(date, actualPosition, MyData.getInstance().getDataVault(username));

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

}
