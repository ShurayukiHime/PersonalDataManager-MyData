package persistence;

import persistence.users.IUser;

public interface IMyData {
	
	IPersonalDataVault getDataVault(IUser user);
	
}
