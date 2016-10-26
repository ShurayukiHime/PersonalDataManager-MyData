package persistence.users;

import persistence.IPersonalDataVault;
import persistence.IService;
import persistence.consents.ServiceConsent;

public interface IAccount {

	public IService getService();

	public IPersonalDataVault getPersonalDataVault();

	public ServiceConsent getActiveDisabledSC();
}
