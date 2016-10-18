package persistence;

import java.util.Date;

class ServiceUser extends MyDataUser {

	private String service; // DA CAMBIARE quando identificazione servizio in qualche modo
	private String username;
	private String password; // cercare modi migliori di salvare una password

	/**
	 * This function creates a specific account for the user to the service. In
	 * this perspective, the username provided should be unique only to this
	 * particular service. The user specified can have only one account to the
	 * service, and in can be created only if there is an Active ServiceConsent for this
	 * service.
	 */
	public ServiceUser(String firstName, String lastName, Date dateOfBirth, String emailAddress, String username,
			String password, String service) {
		super(firstName, lastName, dateOfBirth, emailAddress);
		if (super.getServiceConsentForServiceID(service).getConsentStatus() != ConsentStatus.ACTIVE) {
			System.out.println("Cannot create an account to the service if Consent Status is not Active");
			throw new IllegalArgumentException();
			// eventualmente creare eccezione più specifica
		}
		if (username.trim().isEmpty() || username == null || password.trim().isEmpty() || password == null) {
			System.out.println("Arguments must be not null nor empty");
			throw new IllegalArgumentException();
		}
		this.username = username;
		this.password = password;
		this.service = service;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void addDataConsent(DataConsent DataConsent) {
		// TODO Auto-generated method stub

	}

	public boolean equals(ServiceUser other) {
		return this.username == other.username ? true : false;
	}

	// import di Gson funzionante
	/*
	 * public Gson getServiceUserAsGson() { return null;
	 * 
	 * }
	 */

}
