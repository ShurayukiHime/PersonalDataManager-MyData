package model.consents;

import java.util.Date;

import model.IService;
import model.users.IAccount;
import model.users.IUser;

public class ConsentManager {

	private ConsentManager() {
	}

	public static ServiceConsent giveServiceConsent(IUser user, IService service) {
		// verficare legittimità user
		// il service possiede la chiave pubblica di user
		// il service riceve un token firmato con la chiave privata di user e ne
		// verifica l'autenticità
		// verificare legittimità service
		// creare il consent
		// restituire il consent

		byte[] tokenFromUser = null, tokenSignedUser = null;
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

		byte[] tokenFromService = null, tokenSignedService = null;
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

	public static void addDataConsent(DataConsent dataConsent) {
		// TODO
	}

}
