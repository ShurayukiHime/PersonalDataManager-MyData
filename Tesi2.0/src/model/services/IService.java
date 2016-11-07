package model.services;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.MyData.IDataSet;
import model.security.ISecurityManager;
import model.users.IUser;

/**
 * This interface describes a generic service in the context of MyData. Each
 * service must have a unique name, to be used in comparisons with other
 * services. It must have a security manager to implement the sign and verify
 * methods. Since each service share some common operations, it would be
 * advisable for each new concrete service to implement the abstract class
 * AbstractService instead of this interface.
 * 
 * @author Giada
 *
 */

public interface IService {
	public ISecurityManager getSecurityManager();

	public boolean equals(Object obj);

	public Object provideService(IUser user) throws FileNotFoundException, IOException;

	public String toString();

	public void gatherData(IUser user, IDataSet dataSet);
}
