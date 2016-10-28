package gui;

import java.util.Date;
import java.util.List;

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

	UserInteractor getUserInteractor();
	
}
