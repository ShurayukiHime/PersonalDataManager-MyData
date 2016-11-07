package model.users;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import model.consents.ConsentManager;
import model.consents.ConsentStatus;
import model.consents.DataConsent;
import model.consents.ServiceConsent;
import model.security.ISecurityManager;
import model.services.IService;

/**
 * This class describes a MyData user. Its fields follow a generic guideline
 * specified in the "MyData Stack" specification. Other than personal data
 * fields (as first name, last name, etc.), every user must have a
 * SecurityManager in order to manage his KeyPair and perform sign and verify
 * operations.
 * 
 * Each MyData user may have one account for each Service. Accounts can't be
 * accessed outside this class.
 * 
 * @author Giada
 *
 */

public class MyDataUser implements IUser {

	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String emailAddress;
	private String password; // modo triste per salvare le password, vorrebbero
								// char[]?
	private ISecurityManager securityManager;
	private Set<IAccount> accounts;

	public MyDataUser(String firstName, String lastName, Date dateOfBirth, String emailAddress, String password) {
		super();
		if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty() || dateOfBirth == null
				|| emailAddress == null || emailAddress.isEmpty() || password == null || password.isEmpty())
			throw new IllegalArgumentException("Parameters must not be null or empty.");
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.emailAddress = emailAddress;
		this.password = password;
		this.securityManager = new model.security.SecurityManager();
		this.accounts = new HashSet<IAccount>();
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	@Override
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String getEmailAddress() {
		return emailAddress;
	}

	@Override
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public ISecurityManager getSecurityManager() {
		return securityManager;
	}

	@Override
	public Set<IAccount> getAllAccounts() {
		return this.accounts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyDataUser other = (MyDataUser) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		return true;
	}

	@Override
	public void newAccountAtService(IService service) {
		if (this.hasAccountAtService(service))
			throw new IllegalArgumentException(
					"User " + this.toString() + " already has an account at service " + service.toString() + ".");
		this.accounts.add(new Account(service, ConsentManager.askServiceConsent(this, service)));
	}

	@Override
	public boolean checkIfPasswordEqual(String givenPsw) {
		return givenPsw.equals(this.password);
	}

	public String toString() {
		return this.emailAddress;
	}

	@Override
	public boolean hasAccountAtService(IService service) {
		for (IAccount a : this.accounts)
			if (a.getService().equals(service))
				return true;
		return false;
	}

	@Override
	public ServiceConsent getActiveSCForService(IService service) {
		if (!this.hasAccountAtService(service))
			throw new IllegalArgumentException(
					"User " + this.toString() + " does not have an account at " + service.toString() + ".");
		for (IAccount a : this.accounts)
			if (a.getService().equals(service) && a.getActiveDisabledSC().getConsentStatus() == ConsentStatus.ACTIVE)
				return a.getActiveDisabledSC();
		return null;
	}

	@Override
	public void addDataConsent(DataConsent dataConsent, IService service) {
		for (IAccount a : this.accounts)
			if (a.getService().equals(service))
				a.addDataConsent(dataConsent);
	}

	@Override
	public void addServiceConsent(IService selectedService) {
		if (!this.hasAccountAtService(selectedService))
			throw new IllegalArgumentException(
					"User " + this.toString() + " does not have an account at service " + selectedService.toString());
		for (IAccount a : this.accounts)
			if (a.getService().equals(selectedService))
				a.addServiceConsent(ConsentManager.askServiceConsent(this, selectedService));
	}
}
