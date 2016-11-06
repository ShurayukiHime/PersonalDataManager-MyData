package model.consents;

import java.util.Date;
import java.util.Set;

import model.MyData.IDataSet;
import model.registry.ServiceRegistry;
import model.services.IService;
import model.users.IAccount;
import model.users.IUser;

/**
 * This class provides static methods to ask for various types of consent.
 * 
 * @author Giada
 *
 */

public class ConsentManager {

	private ConsentManager() {
	}

	/**
	 * This method implements a mutual identification protocol between user and
	 * service, by exchanging signed token that the other party can validate.
	 * 
	 * @param user
	 *            The user requesting a new ServiceConsent.
	 * @param service
	 *            The Service which is given consent.
	 * @return a new instance of an Active ServiceConsent.
	 */

	public static ServiceConsent askServiceConsent(IUser user, IService service) {

		// inizializzazione casuale in attesa di approfondimento

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

	/**
	 * This method allows to change the Status of the consent for the specified
	 * Service to the specified User. If the current status is Active or
	 * Disabled, it is possible to choose any other status different from the
	 * current one. Else, if the current ConsentStatus is Withdrawn, no change
	 * is possible.
	 * 
	 * @param user
	 * @param service
	 * @param newStatus
	 * 
	 * @throws IllegalArgumentException
	 *             If attempts to change a Withdrawn ConsentStatus are made.
	 */

	public static void changeServiceConsentStatusForService(IUser user, IService service, ConsentStatus newStatus) {
		for (IAccount a : user.getAllAccounts())
			if (a.getService().equals(service)) {
				ServiceConsent sc = a.getActiveDisabledSC();
				if (sc == null) {
					// non ci sono consent attualmente attivi o disabilitati
					if (newStatus == ConsentStatus.WITHDRAWN) {
						// si sta rimettendo stato withdrawn ad un consent che è
						// già stato ritirato -> non ha senso
						throw new IllegalArgumentException("The consent has already been withdrawn.");
					} // si vuole mettere attivo o disabilitato ad un consent
						// ritirato
					throw new IllegalStateException("Cannot change the status of a Withdrawn Service Consent.");
				}
				// else, il consent è attivo o disabilitato
				sc.ChangeConsentStatus(newStatus);
			}
		// devo aggiornare anche la signature in qualche modo? (metodo update)
		// questo però ricomincerebbe il protocollo di firma, cosa forse poco
		// efficiente se fatta ripetutamente
	}

	/**
	 * This method issues a new Data Consent (for data flowing out of the PDV) if
	 * there is an Active ServiceConsent for the user and service specified. It
	 * also adds the newly created DataConsent to the corresponding IAccount.
	 * 
	 * @param user
	 * @param service
	 * @throws IllegalStateException
	 *             If there is no Active ServiceConsent for the pair user,
	 *             service
	 * @return A valid DataConsent containing the specific Set of IMetadatum for
	 *         that service.
	 */

	public static OutputDataConsent askOutputDataConsent(IUser user, IService service) {
		ServiceConsent consent = user.getActiveSCForService(service);
		if (consent == null)
			throw new IllegalStateException("Cannot issue Output Data Consent for service " + service.toString()
					+ " if Service Consent is not Active.");
		Set<String> metadata = ServiceRegistry.getMetadataForService(service);
		OutputDataConsent outDataConsent = new OutputDataConsent(metadata, consent);
		user.addDataConsent(outDataConsent, service);
		return outDataConsent;
	}

	public static InputDataConsent askInputDataConsent(IUser user, IService service, IDataSet dataSet) {
		ServiceConsent consent = user.getActiveSCForService(service);
		if (consent == null)
			throw new IllegalStateException("Cannot issue Input Data Consent for service " + service.toString()
					+ " if Service Consent is not Active.");
		Set<String> metadata = ServiceRegistry.getMetadataForService(service);
		if (!metadata.containsAll(dataSet.keySet()))
			throw new IllegalArgumentException("The service " + service.toString() + " does not have permission to access some, or all, data types specified in the given DataSet.");
		InputDataConsent inDataConsent = new InputDataConsent(dataSet.keySet(), consent);
		user.addDataConsent(inDataConsent, service);
		return inDataConsent;
	}

}
