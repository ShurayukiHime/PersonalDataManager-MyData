package persistence.consents;

import java.util.Date;

import persistence.IService;
import persistence.users.IAccount;
import persistence.users.IUser;

public class ConsentManager {
	
	private ConsentManager() {
	}

	public static ServiceConsent giveServiceConsent(IUser user, IService service) {
		//verficare legittimità user
			// il service possiede la chiave pubblica di user
			// il service riceve un token firmato con la chiave privata di user e ne verifica l'autenticità
		//verificare legittimità service
		//creare il consent
		//restituire il consent
		
		byte[] tokenFromUser = null, tokenSignedUser;
		tokenSignedUser = user.getSecurityManager().sign(tokenFromUser);
		if (!(service.getSecurityManager().verify(user.getSecurityManager().getPublicKey(), tokenFromUser, tokenSignedUser))) {
			// illegal call
			// throw exception
		} // else
		
		byte[] tokenFromService = null, tokenSignedService;
		tokenSignedService = service.getSecurityManager().sign(tokenFromService);
		if (!( user.getSecurityManager().verify(service.getSecurityManager().getPublicKey(), tokenFromService, tokenSignedService))) {
			// illegal call
			// throw exception
		} // else
		return new ServiceConsent(tokenSignedService, tokenSignedUser, new Date(), service); 
	}

	public static void changeServiceConsentStatusForService(IUser user, IService service, ConsentStatus newStatus) {
		for (IAccount a : user.getAllAccounts()) 
			if (a.getService().equals(service)) {
				ServiceConsent sc = a.getActiveDisabledSC();
				if ( sc == null){
					// non ci sono consent attualmente attivi o disabilitati
					System.out.println("Cannot change the status of a Withdrawn Service Consent!");
					throw new IllegalArgumentException();
					// eventualmente creare eccezione più specifica
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
