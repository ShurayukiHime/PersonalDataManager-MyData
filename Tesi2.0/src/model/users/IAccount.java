package model.users;

import java.util.Set;

import model.MyData.IPersonalDataVault;
import model.consents.ServiceConsent;
import model.services.IService;

public interface IAccount {

	public IService getService();

	public IPersonalDataVault getPersonalDataVault();

	/**
	 * There can be only one ServiceConsent Active or Disabled at a time for a
	 * service. If there is no such consent, this method returns null
	 * 
	 * @return The only Active or Disabled ServiceConsent for
	 *         this service, null otherwise.
	 */
	public ServiceConsent getActiveDisabledSC();

	public Set<ServiceConsent> getAllPastServiceConsents();
}
