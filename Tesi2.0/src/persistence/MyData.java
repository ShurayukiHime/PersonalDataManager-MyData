package persistence;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
		if (user.getAccountForService(service) == null) {
			// throw exception ?
			System.out.println("This user isn't registered to service " + service.toString());
		} // else
		for (IUser u : this.myDataUsers) {
			if (u.equals(user))
				return u.getAccountForService(service).getPersonalDataVault();
		}
		// questo non ha senso :( perchè se si passa il primo if un account esiste sicuramente
		return null;
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
	public void createServiceAccount(IUser user, IService service) {
		user.newAccountAtService(service);
	}
}
