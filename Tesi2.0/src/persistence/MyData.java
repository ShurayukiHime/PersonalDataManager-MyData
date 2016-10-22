package persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import persistence.users.IUser;
import persistence.users.MyDataUser;

public class MyData implements IMyData {

	private static MyData instance;
	private Set<IUser> myDataUsers;
	private Map<ServiceUser, IPersonalDataVault> serviceUsers;

	private MyData() {
		this.serviceUsers = new HashMap<ServiceUser, IPersonalDataVault>();
		this.myDataUsers = new HashSet<IUser>();
	}

	public static IMyData getInstance() {
		if (instance == null)
			instance = new MyData();
		return instance;
	}

	@Override
	public IPersonalDataVault getDataVault(IUser user) {
		return serviceUsers.get(user);
	}

	// è sbagliato creare users che poi non vengono aggiunti?
	// è peggio quello oppure fare un confronto fra tutti gli email address del set in un ciclo for?
	public IUser createMyDataAccount(String firstName, String lastName, Date dateOfBirth, String emailAddress) {
		IUser newUser = new MyDataUser(firstName, lastName, dateOfBirth, emailAddress);
		if (!(this.myDataUsers.add(newUser))) {
			System.out.println("Cannot register two users with the same email address!");
			throw new IllegalArgumentException();
		}
		return newUser;
	}

	// stessa domanda della funzione sopra per creazione accounts
	public void createServiceAccount(IUser user, String username, String password, IService service) {
		ServiceUser newServiceUser = new ServiceUser(user.getFirstName(), user.getLastName(), user.getDateOfBirth(),
				user.getEmailAddress(), username, password, service);
		if (!(this.serviceUsers.keySet().add(newServiceUser))) {
			System.out.println("Cannot create a duplicate account for user " + username);
			throw new IllegalArgumentException();
		}

		// TODO
		// protocollo di scambio delle signatures fra mydatauser e service

		// infine, aggiunta alla mappa di serviceUsers della classe
	}
}
