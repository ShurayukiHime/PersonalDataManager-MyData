package gui;

public interface UserInteractor {
	void showMessage(String message);

	void shutDownApplication();
	
	void showErrorMessage(String message);
	
	void showInfoMessage (String message);

	void showPlainMessage(String message);
}
