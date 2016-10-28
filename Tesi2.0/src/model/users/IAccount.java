package model.users;

import java.util.Set;

import model.MyData.IPersonalDataVault;
import model.consents.ServiceConsent;
import model.services.IService;

public interface IAccount {

	public IService getService();

	public IPersonalDataVault getPersonalDataVault();

	public ServiceConsent getActiveDisabledSC();

	public Set<ServiceConsent> getAllPastServiceConsents();
}
