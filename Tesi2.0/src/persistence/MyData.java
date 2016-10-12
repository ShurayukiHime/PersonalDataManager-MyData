package persistence;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyData implements IMyData {

	private static MyData instance;
	private Map<ServiceUser, IPersonalDataVault> serviceUsers;

	private MyData() {
		this.serviceUsers = new HashMap<>();
	}

	public static IMyData getInstance() {
		if (instance == null)
			instance = new MyData();
		return instance;
	}

	@Override
	public IPersonalDataVault getDataVault(String username) {
		return serviceUsers.get(username);
	}

	// da cambiare l'identificazione del servizio!
	// che ci devo fare con tutti questi try catch?
	public void createServiceAccount(MyDataUser user, String username, String password, String service) {
		if (user.getServiceConsentForServiceID(service) != null) {
			System.out.println("Cannot create a duplicate account for user " + username);
			return;
		}
		try {
			user.addServiceConsent(new ServiceConsent(user.signWithPrivateKey(), new Date(), service));
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			// stampare messaggio di errore sulla chiave che non va bene,
			// probabile refactoring
			// lanciare eccezione più generica, non è possibile creare il service user!
			e.printStackTrace();
		}
		this.serviceUsers.put(new ServiceUser(user, username, password), new PersonalDataVault());
	}
}
