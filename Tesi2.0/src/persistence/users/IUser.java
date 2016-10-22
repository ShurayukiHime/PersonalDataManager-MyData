package persistence.users;

import java.util.Date;
import java.util.Set;

import persistence.IConsent;
import persistence.IService;

public interface IUser {

	public String getFirstName();

	public void setFirstName(String firstName);

	public String getLastName();

	public void setLastName(String lastName);

	public Date getDateOfBirth();

	public void setDateOfBirth(Date dateOfBirth);

	public String getEmailAddress();

	public void setEmailAddress(String emailAddress);

	public boolean equals(Object other);
	// ogni mdu deve avere un consent manager, magari anche espresso tramite
	// interfaccia così non dipende dalla singola implementazione, però forse è
	// meglio non mettere gli accessor perchè non deve essere visibile
	// all'esterno?

	
	public Set<IConsent> getAllConsentsForService(IService service);
	// e questa cosa restituisce?

	public void newAccountAtService(IService service);
}
