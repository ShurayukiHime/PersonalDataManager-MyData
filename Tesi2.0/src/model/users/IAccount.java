package model.users;

import java.util.Set;

import model.IService;
import model.MyData.IPersonalDataVault;
import model.consents.ServiceConsent;

public interface IAccount {

	public IService getService();

	public IPersonalDataVault getPersonalDataVault();

	public ServiceConsent getActiveDisabledSC();

	public Set<ServiceConsent> getAllPastServiceConsents();
}
