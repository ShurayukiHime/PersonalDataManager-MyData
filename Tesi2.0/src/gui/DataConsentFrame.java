package gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DataConsentFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JTextArea mainTextArea;
	private IController controller;
	
	public DataConsentFrame (IController controller) {
		super("Data Consents issued:");
		this.controller = controller;
		this.initGUI();
	}
	
	
	public void initGUI() {
		this.mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 1));
		{
			mainTextArea = new JTextArea(15, 50);
			mainTextArea.setEditable(false);
			mainTextArea.setLineWrap(true);
			JScrollPane scrollPane = new JScrollPane(mainTextArea);
			
			mainPanel.add(scrollPane);
		}
		
		this.add(mainPanel);
		this.pack();
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public void updateTextArea(String text) {
		if (text != null)
			this.mainTextArea.setText(text);
	}
	
}
