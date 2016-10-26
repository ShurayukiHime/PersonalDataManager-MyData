package persistence;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import persistence.users.IAccount;
import persistence.users.IUser;
import persistence.users.MyDataUser;

public class MyData implements IMyData {

	private static MyData instance;
	private Set<IUser> myDataUsers;

	private MyData() {
		this.myDataUsers = new HashSet<IUser>();
	}

	public static IMyData getInstance() {
		if (instance == null)
			instance = new MyData();
		return instance;
	}

	@Override
	public IPersonalDataVault getDataVault(IUser user, IService service) {
		for (IAccount a : user.getAllAccounts()) {
			if (a.getService().equals(service))
				return a.getPersonalDataVault();
		} // else
		// throw exception or return null?
		System.out.println("This user isn't registered to service " + service.toString());
	}

	// è sbagliato creare users che poi non vengono aggiunti?
	// è peggio quello oppure fare un confronto fra tutti gli email address del set in un ciclo for?
	@Override
	public IUser createMyDataAccount(String firstName, String lastName, Date dateOfBirth, String emailAddress) {
		IUser newUser = new MyDataUser(firstName, lastName, dateOfBirth, emailAddress);
		if (!(this.myDataUsers.add(newUser))) {
			//System.out.println("Cannot register two users with the same email address!");
			throw new IllegalStateException("Cannot register two users with the same email address!");
			// è l'eccezione giusta?
		}
		return newUser;
	}
	
	// returs null if user not found
	// dovrebbe esserci una password...
	@Override
	public IUser loginUser (String email)
	{
		for (IUser user : this.myDataUsers) {
			if (user.getEmailAddress().equals(email))
				return user;
		}
		return null;
	}
	// stessa domanda della funzione sopra per creazione accounts
	public void createServiceAccount(IUser user, IService service) {
		user.newAccountAtService(service);
	}
}
