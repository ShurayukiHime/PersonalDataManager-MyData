package model.MyData;

import java.util.Set;

public interface IDataSet {

	public void put(String typeConst, Object obj);

	public Object get(String typeConst);

	public Set<String> keySet();
}
