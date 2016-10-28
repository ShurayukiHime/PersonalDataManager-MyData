package model.MyData;

import java.util.Date;

import model.IService;
import model.users.IUser;

public interface IMyData {
	
	IPersonalDataVault getDataVault(IUser user, IService service);

	IUser loginUser(String email, String password);

	IUser createMyDataAccount(String firstName, String lastName, Date dateOfBirth, String emailAddress, String password);
	
}
