package model.MyData;

import java.util.Date;

import model.consents.DataConsent;
import model.consents.IDataSet;
import model.services.IService;
import model.users.IUser;

public interface IMyData {

	public IUser loginUser(String email, String password);

	public IUser createMyDataAccount(String firstName, String lastName, Date dateOfBirth, String emailAddress,
			String password);

	public IDataSet getDataSetForDataConsent(DataConsent dataConsent);

}
