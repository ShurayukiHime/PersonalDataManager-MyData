package persistence;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class describes a generic MyData User. This kind of description does not
 * depend on the particular service chosen, so it should be implemented as a
 * starting base for the account the end-user creates at the service.
 */

public class MyDataUser {
	private final String keyAlgorithm = "DSA";
	private final String randomAlgorithm = "SHA1PRNG";
	//problema dell'username come chiave della mappa, per come l'ha pensato nicolas
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String emailAddress;
	// penso di aver bisogno di una password da qualche parte, ad esempio
	// metti che l'utente vuole cambiare chiavi, bisogna chiedere conferma
	// o in generale quando bisogna dare un consent (magari non tutte le volte?)
	// però è giusto che stia qui dentro?
	// forse è meglio fare una classe nascosta che contiene le chiavi e si occupa di generazione delle stesse,
	// verifica, firma and so on..?
	// provvisoriamente metto le chiavi qui
	private KeyPair keyPair;
	private List<ServiceConsent> serviceConsents;

	//potrei volerlo chiamare dall'esterno?
	private KeyPair generateKeys(String keyAlgorithm, String randomAlgorithm) throws NoSuchAlgorithmException {
		//era meglio cablarli nel codice? (non so quanto sia utile metterli nel costruttore)
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(keyAlgorithm);
		SecureRandom random = SecureRandom.getInstance(randomAlgorithm);
		//1024 e gli altri dati vanno bene?
		keyPairGen.initialize(1024, random);
		return keyPairGen.generateKeyPair();
	}
	
	public MyDataUser() {
		super();
	}

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
		try {
			this.keyPair = generateKeys(keyAlgorithm, randomAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.serviceConsents = new ArrayList<ServiceConsent>();
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

	public List<ServiceConsent> getAllServiceConsents() {
		return serviceConsents;
	}
	
	//da cambiare quando avrò un modo di identificare i servizi!!
	//ritorna il consent se esiste, altrimenti ritorna null
	public ServiceConsent getServiceConsentForServiceID (String service) {
		//TODO
		return null;
	}

	public void addServiceConsent(ServiceConsent serviceConsent) {
		//devo controllare che il service consent passato sia realmente firmato da me...?
		if (serviceConsent != null) {
			serviceConsents.add(serviceConsent);
		}
	}
	
	public PublicKey getPublicKey() {
		return keyPair.getPublic();
	}
	
	public Signature signWithPrivateKey() throws InvalidKeyException, NoSuchAlgorithmException {
		Signature signature = Signature.getInstance(keyAlgorithm);
		signature.initSign(keyPair.getPrivate());
		return signature;
	}
}
