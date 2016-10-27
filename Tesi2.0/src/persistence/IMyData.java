package persistence;

import java.util.Date;

import persistence.users.IUser;
import persistence.users.MyDataUser;

public interface IMyData {
	
	IPersonalDataVault getDataVault(IUser user, IService service);

	IUser loginUser(String email, String password);

	IUser createMyDataAccount(String firstName, String lastName, Date dateOfBirth, String emailAddress, String password);
	
}
