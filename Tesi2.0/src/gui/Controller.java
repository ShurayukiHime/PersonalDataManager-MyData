package gui;

import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import model.services.IService;

public interface Controller {

	public void createMyDataUser(String firstName, String lastName, Date dateOfBirth, String emailAddress, String password);

	public void logInUser(String email, String password);

	public void toggleStatus(IService selectedService, boolean status);

	public String getAllPastSConsents(IService selectedService);

	public void addService(IService selectedService);

	public void withdrawConsentForService(IService selectedService);

	public List<IService> getAllActiveServicesForUser();

	public boolean getADStatusForService(IService selectedService);

	public UserInteractor getUserInteractor();

	public void setActualPosition(double lat, double lon);

	public void setPanelDate(JTextField day, JTextField month, JTextField year, JTextField hour, JTextField min);

	public JPanel getServicePanel(IService selectedService);
	
}
