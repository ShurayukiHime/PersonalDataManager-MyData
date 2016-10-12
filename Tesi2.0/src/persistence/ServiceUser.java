package persistence;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;

class ServiceUser extends MyDataUser {

	private MyDataUser user;
	private String username;
	private String password; //esisteranno modi migliori di salvare una password
	private List<DataConsent> dataConsents;
	
	/**
	 * This function creates a specific account for the user to the service. In this perspective, the username provided should
	 * be unique only to this particular service. The user specified can have only one account to the service, and in can be created
	 * only if there is a ServiceConsent for this service.
	 */
	public ServiceUser(MyDataUser user, String username, String password) {
		super();
		if (user == null || username.trim().isEmpty() || username == null || password.trim().isEmpty() || password == null) {
			System.out.println("Arguments should be not null nor empty");
			throw new IllegalArgumentException();
		}
		this.user = user;
		this.username = username;
		this.password = password;
		this.dataConsents = new ArrayList<DataConsent>();
	}

	public MyDataUser getUser() {
		return user;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<DataConsent> getDataConsents() {
		return dataConsents;
	}
	
	public void addDataConsent (DataConsent dataConsent) {
		//devo controllare che il data consent passato sia realmente firmato da me...?
		if (dataConsent != null) 
			this.dataConsents.add(dataConsent);
	}

	public boolean equals(ServiceUser other) {
		return this.username == other.username ? true : false;
	}

	@Override
	public Signature signWithPrivateKey() throws InvalidKeyException, NoSuchAlgorithmException {
		return user.signWithPrivateKey();
	}

}
