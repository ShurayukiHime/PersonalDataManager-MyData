package model.services;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.security.ISecurityManager;
import model.users.IUser;

public interface IService {
	public ISecurityManager getSecurityManager();

	public boolean equals(Object obj);

	public Object provideService(IUser user) throws FileNotFoundException, IOException;

	public String toString();
}
