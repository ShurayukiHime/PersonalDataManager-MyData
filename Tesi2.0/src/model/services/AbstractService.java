package model.services;

import model.MyData.IMyData;
import model.MyData.MyData;
import model.consents.ConsentManager;
import model.consents.DataConsent;
import model.consents.IDataSet;
import model.security.ISecurityManager;
import model.users.IUser;

public abstract class AbstractService implements IService {
	
	private IMyData myDataInstance;
	private ISecurityManager securityManager;
	
	protected AbstractService () {
		this.myDataInstance = MyData.getInstance();
		this.securityManager = new model.security.SecurityManager();
	}

	@Override
	public Object provideService(IUser user) {
		DataConsent dataConsent = ConsentManager.askDataConsent(user, this);
		IDataSet dataSet = myDataInstance.getDataSetForDataConsent(dataConsent);
		return this.concreteService(dataSet);
	}

	@Override
	public ISecurityManager getSecurityManager() {
		return this.securityManager;
	}

	protected abstract Object concreteService(IDataSet dataSet);
}
