package persistence;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ConsentManager {

	private MyDataUser user;
	private Map<ServiceConsent, List<DataConsent>> dataConsents;
	private KeyPair keyPair;
	
	private final String keyAlgorithm = "DSA";
	private final String randomAlgorithm = "SHA1PRNG";
	//da spostare nel nuovo security manager
	
	protected ConsentManager(MyDataUser user) {
		this.user = user;
		this.dataConsents = new HashMap<ServiceConsent, List<DataConsent>>();
		try {
			this.keyPair = generateKeys(keyAlgorithm, randomAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private KeyPair generateKeys(String keyAlgorithm, String randomAlgorithm) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(keyAlgorithm);
		SecureRandom random = SecureRandom.getInstance(randomAlgorithm);
		//1024 e gli altri dati vanno bene?
		keyPairGen.initialize(1024, random);
		return keyPairGen.generateKeyPair();
	}

	public Set<ServiceConsent> getAllServiceConsents() {
		return this.dataConsents.keySet();
	}

	// da FARE quando capisco come identificare i servizi
	// è giusto dare per scontato che ritorna quello attivo in questo momento?
	// e se non ce ne sono di attivi in questo momento?
	public ServiceConsent getServiceConsentForServiceID(String service) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addServiceConsent(ServiceConsent serviceConsent) {
		if (serviceConsent != null)
			this.dataConsents.put(serviceConsent, new ArrayList<DataConsent>());
		//invocata alla fine del protocollo, ora ipotesi x vedere mydata & service user
	}
	
	// da RIFARE quando avrò l'identificatore dei servizi
	//siccome può esserci un solo consent attivo alla volta, si cambia lo status di quello attivo
	public void changeServiceConsentStatusForService (String service, ConsentStatus newStatus) {
		ServiceConsent consent = null;
		for (ServiceConsent sc : this.dataConsents.keySet()) {
			if (sc.getService().equals(service) && (sc.getTimestampWithdrawn() == null))
				consent = sc;
		}
		if (consent == null) {
			//non ci sono consent attualmente attivi o disabilitati
			System.out.println("Cannot change the status of a Withdrawn Service Consent!");
			throw new IllegalArgumentException();
			// eventualmente creare eccezione più specifica
		}
		// else, il consent è attivo o disabilitato
		consent.ChangeConsentStatus(newStatus);
	}
	
	public void addDataConsent(DataConsent dataConsent) {
		//TODO
	}
}
