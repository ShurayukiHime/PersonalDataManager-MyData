package persistence.users;

import java.util.Set;

import persistence.IPersonalDataVault;
import persistence.IService;
import persistence.consents.ServiceConsent;

public interface IAccount {

	public IService getService();

	public IPersonalDataVault getPersonalDataVault();

	public ServiceConsent getActiveDisabledSC();

	public Set<ServiceConsent> getAllPastServiceConsents();
}
