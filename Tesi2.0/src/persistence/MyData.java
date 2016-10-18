package persistence;

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
	public IPersonalDataVault getDataVault(ServiceUser user) {
		return serviceUsers.get(user);
	}

	// da cambiare l'identificazione del servizio!
	public void createServiceAccount(MyDataUser user, String username, String password, String service) {
		if (user.getServiceConsentForServiceID(service) != null) {
			System.out.println("Cannot create a duplicate account for user " + username);
			return;
		}
		
		//TODO
		//protocollo di scambio delle signatures fra mydatauser e service
		
		//infine, aggiunta alla mappa di serviceUsers della classe
	}
}
