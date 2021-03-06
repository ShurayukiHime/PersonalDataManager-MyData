package model.users;

import java.util.List;
import java.util.Set;

import model.consents.DataConsent;
import model.consents.ServiceConsent;
import model.services.IService;

public interface IAccount {

	public IService getService();

	/**
	 * There can be only one ServiceConsent Active or Disabled at a time for a
	 * service. If there is no such consent, this method returns null
	 * 
	 * @return The only Active or Disabled ServiceConsent for
	 *         this service, null otherwise.
	 */
	public ServiceConsent getActiveDisabledSC();

	public Set<ServiceConsent> getAllServiceConsents();

	public void addDataConsent(DataConsent dataConsent);

	public void addServiceConsent(ServiceConsent serviceConsent);

	public List<DataConsent> getAllDataConsents(ServiceConsent sc);
}
