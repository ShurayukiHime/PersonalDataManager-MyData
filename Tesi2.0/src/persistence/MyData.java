package persistence;

import java.util.HashMap;
import java.util.Map;

public class MyData implements IMyData {
	
	private static MyData instance;
	private Map<String,IPersonalDataVault> users;
	
	private MyData() {
		this.users = new HashMap<>();
		users.put("prova", new PersonalDataVault());
	}
	
	public static IMyData getInstance() {
		if (instance == null)
			instance = new MyData();
		return instance;
	}

	@Override
	public IPersonalDataVault getDataVault(String username) {
		return users.get(username);
	}
}
