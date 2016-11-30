package model.MyData;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import model.consents.ConsentStatus;
import model.consents.InputDataConsent;
import model.consents.OutputDataConsent;
import model.services.IService;
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
			char[] password) {
		IUser newUser = new MyDataUser(firstName, lastName, dateOfBirth, emailAddress, password);
		if (!(this.myDataUsers.add(newUser))) {
			throw new IllegalStateException("Cannot register two users with the same email address.");
		}
		return newUser;
	}

	// returs null if user not found
	@Override
	public IUser loginUser(String email, char[] password) {
		for (IUser user : this.myDataUsers) {
			if (user.getEmailAddress().equals(email) && user.checkIfPasswordEqual(password))
				return user;
		}
		// se arriva qui, user o password incorretti!
		throw new IllegalArgumentException("Wrong credentials.");
	}

	// stessa domanda della funzione sopra per creazione accounts
	@Override
	public void createServiceAccount(IUser user, IService service) {
		user.newAccountAtService(service);
	}

	@Override
	public IDataSet getDataSetForOutputDataConsent(OutputDataConsent outputDataConsent) {
		if (outputDataConsent.getServiceConsent().getConsentStatus() != ConsentStatus.ACTIVE)
			throw new IllegalStateException(
					"Cannot get DataSet if DataConsent is not valid. ServiceConsent is not Active.");
		IUser user = outputDataConsent.getServiceConsent().getUser();
		IPersonalDataVault pdv = user.getPersonalDataVault();
		return pdv.getData(outputDataConsent.getTypesConst());
	}

	@Override
	public void saveDataSet(IDataSet dataSet, InputDataConsent inDataConsent) {
		if (inDataConsent.getServiceConsent().getConsentStatus() != ConsentStatus.ACTIVE)
			throw new IllegalStateException(
					"Cannot save DataSet if DataConsent is not valid. ServiceConsent is not Active.");
		IUser user = inDataConsent.getServiceConsent().getUser();
		IPersonalDataVault pdv = user.getPersonalDataVault();
		pdv.saveData(dataSet);
	}

	@Override
	public void issueNewServiceConsent(IService selectedService, IUser authenticatedUser) {
		if (!this.myDataUsers.contains(authenticatedUser))
			throw new IllegalArgumentException(
					"User " + authenticatedUser.toString() + " does not have an account at MyData.");
		authenticatedUser.addServiceConsent(selectedService);
	}
}
