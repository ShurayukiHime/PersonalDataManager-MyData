package model.MyData;

import java.util.Date;

import model.consents.InputDataConsent;
import model.consents.OutputDataConsent;
import model.users.IUser;

public interface IMyData {

	public IUser loginUser(String email, String password);

	public IUser createMyDataAccount(String firstName, String lastName, Date dateOfBirth, String emailAddress,
			String password);

	public IDataSet getDataSetForOutputDataConsent(OutputDataConsent outputDataConsent);

	public void saveDataSet(IDataSet dataSet, InputDataConsent inDataConsent);

}
