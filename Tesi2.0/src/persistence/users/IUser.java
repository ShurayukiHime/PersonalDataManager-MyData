package persistence.users;

import java.util.Date;

import persistence.ISecurityManager;
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

	public ISecurityManager getSecurityManager();

	public boolean equals(Object other);
	// ogni mdu deve avere un consent manager, magari anche espresso tramite
	// interfaccia cos� non dipende dalla singola implementazione, per� forse �
	// meglio non mettere gli accessor perch� non deve essere visibile
	// all'esterno?


	public void newAccountAtService(IService service);

	public IAccount getAccountForService(IService service);



}
