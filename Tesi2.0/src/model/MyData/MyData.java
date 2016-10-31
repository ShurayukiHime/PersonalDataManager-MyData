package model.MyData;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import model.consents.DataConsent;
import model.consents.IDataSet;
import model.services.IService;
import model.users.IAccount;
import model.users.IUser;
import model.users.MyDataUser;

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

	// � sbagliato creare users che poi non vengono aggiunti?
	// � peggio quello oppure fare un confronto fra tutti gli email address del
	// set in un ciclo for?
	@Override
	public IUser createMyDataAccount(String firstName, String lastName, Date dateOfBirth, String emailAddress,
			String password) {
		IUser newUser = new MyDataUser(firstName, lastName, dateOfBirth, emailAddress, password);
		if (!(this.myDataUsers.add(newUser))) {
			throw new IllegalStateException("Cannot register two users with the same email address!");
		}
		return newUser;
	}

	// returs null if user not found
	// dovrebbe esserci una password...
	@Override
	public IUser loginUser(String email, String password) {
		for (IUser user : this.myDataUsers) {
			if (user.getEmailAddress().equals(email) && user.checkIfPasswordEqual(password))
				return user;
		}
		// se arriva qui, user o password incorretti!
		throw new IllegalArgumentException("Wrong credentials.");
	}

	// stessa domanda della funzione sopra per creazione accounts
	public void createServiceAccount(IUser user, IService service) {
		user.newAccountAtService(service);
	}

	@Override
	public IDataSet getDataSetForDataConsent(DataConsent dataConsent) {
		return null;
		//TODO
	}

}