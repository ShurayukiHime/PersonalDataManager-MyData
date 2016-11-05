package model.services;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.MyData.IDataSet;
import model.MyData.IMyData;
import model.MyData.MyData;
import model.consents.ConsentManager;
import model.consents.DataConsent;
import model.security.ISecurityManager;
import model.users.IUser;

public abstract class AbstractService implements IService {

	private IMyData myDataInstance;
	private ISecurityManager securityManager;

	protected AbstractService() {
		this.myDataInstance = MyData.getInstance();
		this.securityManager = new model.security.SecurityManager();
		this.registerService();
	}

	/**
	 * This method implements that part of the business logic which is
	 * common to each service: it asks the ConsentManager for a DataConsent and
	 * it receives the DataSet associated with that consent. After that, an
	 * abstract method is called, which will contain the specific implementation
	 * of the service considered.
	 */
	@Override
	public Object provideService(IUser user) throws FileNotFoundException, IOException {
		DataConsent dataConsent = ConsentManager.askDataConsent(user, this);
		IDataSet dataSet = myDataInstance.getDataSetForDataConsent(dataConsent);
		return this.concreteService(dataSet);
	}

	@Override
	public ISecurityManager getSecurityManager() {
		return this.securityManager;
	}

	protected abstract Object concreteService(IDataSet dataSet) throws FileNotFoundException, IOException;

	/**
	 * This method is called at the instantiation of the implementing object,
	 * which will be the concrete service. It should register the service to the
	 * Service Registry, as specified in the MyData Architecture Specifications.
	 */
	protected abstract void registerService();

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);
}
