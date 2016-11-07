package model.MyData;

import java.util.Set;

public interface IDataSet {

	public void put(String typeConst, Object obj);

	public Object getObject(String typeConst);

	public Set<String> getKeys();
}
