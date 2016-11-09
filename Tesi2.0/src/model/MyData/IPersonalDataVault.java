package model.MyData;

import java.util.Set;

public interface IPersonalDataVault {

	public IDataSet getData(Set<String> typesConst);

	public void saveData(IDataSet dataSet);
}
