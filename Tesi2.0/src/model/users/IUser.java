package model.users;

import java.util.Date;
import java.util.Set;

import model.consents.ServiceConsent;
import model.security.ISecurityManager;
import model.services.IService;

public interface IUser {

	public String getFirstName();

	public void setFirstName(String firstName);

	public String getLastName();

	public void setLastName(String lastName);

	public Date getDateOfBirth();

	public void setDateOfBirth(Date dateOfBirth);

	public String getEmailAddress();

	public void setEmailAddress(String emailAddress);

	public ISecurityManager getSecurityManager();
	
	public int hashCode();

	public boolean equals(Object other);

	public void newAccountAtService(IService service);

	public Set<IAccount> getAllAccounts();

	boolean checkIfPasswordEqual(String givenPsw);

	boolean hasAccountAtService(IService service);

	ServiceConsent getActiveSCForService(IService service);



}
