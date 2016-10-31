package model.consents;

import java.util.Date;
import java.util.Set;

import model.registry.IMetadatum;
import model.registry.ServiceRegistry;
import model.services.IService;
import model.users.IAccount;
import model.users.IUser;

public class ConsentManager {

	private ConsentManager() {
	}

	public static ServiceConsent askServiceConsent(IUser user, IService service) {

		//inizializzazione casuale in attesa di approfondimento
		
		byte[] tokenFromUser = "Example Token From User".getBytes(), tokenSignedUser = null;
		try {
			tokenSignedUser = user.getSecurityManager().sign(tokenFromUser);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new SecurityException("Encountered error during sign process - user side.");
		}
		if (!(service.getSecurityManager().verify(user.getSecurityManager().getPublicKey(), tokenFromUser,
				tokenSignedUser))) {
			throw new SecurityException("Encountered error during verify process - Service side.");
		} // else

		byte[] tokenFromService = "Example Token From Service".getBytes(), tokenSignedService = null;
		try {
			tokenSignedService = service.getSecurityManager().sign(tokenFromService);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SecurityException("Encountered error during sign process - Service side.");
		}
		if (!(user.getSecurityManager().verify(service.getSecurityManager().getPublicKey(), tokenFromService,
				tokenSignedService))) {
			throw new SecurityException("Encountered error during verify process - user side.");
		} // else
		return new ServiceConsent(tokenSignedService, tokenSignedUser, new Date(), service, user);
	}

	public static void changeServiceConsentStatusForService(IUser user, IService service, ConsentStatus newStatus) {
		for (IAccount a : user.getAllAccounts())
			if (a.getService().equals(service)) {
				ServiceConsent sc = a.getActiveDisabledSC();
				if (sc == null) {
					// non ci sono consent attualmente attivi o disabilitati
					if (newStatus == ConsentStatus.WITHDRAWN) {
						throw new IllegalArgumentException("The consent has already been withdrawn.");
					} // else
					throw new IllegalStateException("Cannot change the status of a Withdrawn Service Consent!");
				}
				// else, il consent è attivo o disabilitato
				sc.ChangeConsentStatus(newStatus);
			}
		// devo aggiornare anche la signature in qualche modo? (metodo update)
		// questo però ricomincerebbe il protocollo di firma, cosa forse poco
		// efficiente se fatta ripetutamente
	}

	public static DataConsent askDataConsent(IUser user, IService service) {
		ServiceConsent consent = user.getActiveSCForService(service);
		if (consent == null)
			throw new IllegalStateException("Cannot issue Data Consent for service " + service.toString() + " if Service Consent is not Active.");
		Set<IMetadatum> metadata = ServiceRegistry.getMetadataForService(service);
		return new DataConsent(metadata, consent);
	}

}
