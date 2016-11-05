package model.MyData;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import model.consents.ConsentStatus;
import model.consents.DataConsent;
import model.registry.IMetadata;
import model.registry.Metadata;
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


	// è sbagliato creare users che poi non vengono aggiunti?
	// è peggio quello oppure fare un confronto fra tutti gli email address del
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
		//bisogna controllare che il service consent a cui fa riferimento sia valido al momento presente
		//si crea un idataset vuoto
		//per ogni tipo nel set 
		//si interroga il pdv e si chiede quel dato
		// si aggiunge nel dataset la coppia tag, dato
		
		if (dataConsent.getServiceConsent().getConsentStatus() != ConsentStatus.ACTIVE) 
			throw new IllegalStateException("Cannot get DataSet if DataConsent is not valid. ServiceConsent is not Active.");
		IUser user = dataConsent.getServiceConsent().getUser();
		IService service = dataConsent.getServiceConsent().getService();
		IAccount account = null;
		for (IAccount a : user.getAllAccounts()) {
			if (a.getService().equals(service))
				account = a;
		}
		IPersonalDataVault pdv = account.getPersonalDataVault();
		return pdv.getData(dataConsent.getTypesConst());
	}

}
