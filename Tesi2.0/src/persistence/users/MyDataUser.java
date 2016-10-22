package persistence.users;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import persistence.ConsentManager;
import persistence.IConsent;
import persistence.ISecurityManager;
import persistence.IService;

public class MyDataUser implements IUser {

	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String emailAddress;
	private ConsentManager consentManager;
	private ISecurityManager securityManager;
	private Set<IAccount> accounts;

	public MyDataUser(String firstName, String lastName, Date dateOfBirth, String emailAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.emailAddress = emailAddress;
		this.consentManager = new ConsentManager();

		// ma cosa??
		this.securityManager = (ISecurityManager) new SecurityManager();

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

	public ConsentManager getConsentManager() {
		return consentManager;
	}

	public ISecurityManager getSecurityManager() {
		return securityManager;
	}

	// returns null if account doesn't exist
	public IAccount getAccountForService(IService service) {
		for (IAccount a : this.accounts) {
			if (a.getService().equals(service))
				return a;
		}
		return null;
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
		if (this.getAccountForService(service) != null) {
			// throw exception? this is an illegal action
			System.out.println("An account already exists at service " + service.toString());
		}
		this.accounts.add(new Account(service, this.consentManager.giveServiceConsent(this, service)));
	}
	
	@Override
	public Set<IConsent> getAllConsentsForService(IService service) {
		// TODO Auto-generated method stub
		return null;
	}

}
