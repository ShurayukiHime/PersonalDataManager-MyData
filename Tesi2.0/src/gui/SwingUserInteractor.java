package gui;

import javax.swing.JOptionPane;

public class SwingUserInteractor implements UserInteractor {

	public SwingUserInteractor() {
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public void shutDownApplication() {
		System.exit(1);
	}

	@Override
	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error:", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void showInfoMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Info:",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	@Override
	public void showPlainMessage (String message) {
		JOptionPane.showMessageDialog(null, message, "Info:",
				JOptionPane.PLAIN_MESSAGE);
	}

}
