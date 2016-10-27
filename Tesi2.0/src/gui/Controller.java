package gui;

import java.util.Date;

import persistence.IService;

public interface Controller {

	public void createMyDataUser(String firstName, String lastName, Date dateOfBirth, String emailAddress, String password);

	public void logInUser(String email, String password);

	public void toggleStatus(IService selectedService, boolean status);

	public String getAllPastSConsents(IService selectedService);

	public void addService();
	
}
