package persistence.users;

import persistence.IPersonalDataVault;
import persistence.IService;

public interface IAccount {

	public IService getService();

	public IPersonalDataVault getPersonalDataVault();
}
