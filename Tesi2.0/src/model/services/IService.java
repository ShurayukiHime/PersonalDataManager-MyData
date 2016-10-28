package model.services;

import model.security.ISecurityManager;
import model.users.IUser;

public interface IService {
	public ISecurityManager getSecurityManager();

	public boolean equals(Object obj);

	public Object provideService(IUser user);
}
