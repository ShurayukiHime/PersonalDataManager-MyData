package persistence;

import java.util.Date;
import java.util.Set;

/**
 * This class describes a generic MyData User. This kind of description does not
 * depend on the particular service chosen, so it should be implemented as a
 * starting base for the account the end-user creates at the service.
 */

public class MyDataUser {
	
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String emailAddress;
	private ConsentManager consentManager;
	// penso ci sia bisogno di una password da qualche parte, ad esempio
	// metti che l'utente vuole cambiare chiavi, bisogna chiedere conferma
	// o in generale quando bisogna dare un consent (magari non tutte le volte?)
	// però è giusto che stia qui dentro?
	// anche perchè c'è una password in service user e forse potremmo avere un problema
	
	// forse è meglio fare una classe nascosta che contiene le chiavi e si occupa di generazione delle stesse,
	// verifica, firma and so on..?
	// andrebbe bene, ma rimarrebbe cmq il problema di chi la invoca, perchè o si hanno 2 pass (una generica, in mydatausr,
	// una particolare per il servizio in serviceusr) oppure ne si ha una sola per tutto in mydatausr
	// nessun usr realistico si ricorda due pass, di cui una cambia x ogni servizio
	

	/**
	 * This method creates a new MyData user.
	 * It also initialises an empty collection of Consent(s), and creates a new KeyPair for the user.
	 */
	public MyDataUser(String firstName, String lastName, Date dateOfBirth, String emailAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.emailAddress = emailAddress;
		this.consentManager = new ConsentManager(this);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Set<ServiceConsent> getAllServiceConsents() {
		return this.consentManager.getAllServiceConsents();
	}
	
	//da CAMBIARE quando avrò un modo di identificare i servizi!
	//ritorna il consent se esiste, altrimenti ritorna null
	public ServiceConsent getServiceConsentForServiceID (String service) {
		return this.consentManager.getServiceConsentForServiceID(service);
	}

	public void registerServiceConsent(ServiceConsent serviceConsent) {
		//TODO
		//implementare protocollo di firma da entrambe le parti, dipende in parte dall'identificazione del servizio
		//dopo che si è concluso il protocollo di firma (happy ending) delega al consent manager la creazione del consent
	}
	
}
