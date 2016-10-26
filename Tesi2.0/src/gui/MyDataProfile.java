package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import persistence.IService;

public class MyDataProfile extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	
	private JPanel welcomePanel;
	private JTextField nomeTField;
	private JTextField cognomeTField;
	private JSpinner datePicker;
	private JTextField emailAddressTField;
	private JPasswordField pswPField;
	
	private JButton signInButton;
	private JButton signUpButton;
	
	
	private JPanel profilePanel;
	private JComboBox<IService> servicesComboBox;
	
	private JButton toggleDisabledStatus;
	private JButton setWithdrawnStatus;
	private JButton viewPastSConsents;
	private JButton addService;
	private JButton requestService;
	public MyDataProfile (Controller controller) {
		super("Benvenuto in MyData");
		this.controller = controller;
		initGUI();
	}

	private void initGUI() {
		welcomePanel = new JPanel();
		welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.LINE_AXIS));
		{
			JPanel registrationPanel = new JPanel();
			registrationPanel.setLayout(new BoxLayout(registrationPanel, BoxLayout.PAGE_AXIS));
			registrationPanel.setBorder(new TitledBorder(new EtchedBorder(), "Sign Up"));
			emailAddressTField = new JTextField(15);
			emailAddressTField.setText("email@gmail.com");
			emailAddressTField.setEditable(true);
			{
				nomeTField = new JTextField(15);
				nomeTField.setText("Nome");
				nomeTField.setEditable(true);
				cognomeTField = new JTextField(15);
				cognomeTField.setText("Cognome");
				cognomeTField.setEditable(true);
				datePicker = new JSpinner(new SpinnerDateModel());
				JSpinner.DateEditor dateDEditor = new JSpinner.DateEditor(datePicker, "dd/MM/yyyy");
				datePicker.setEditor(dateDEditor);
				
				signUpButton = new JButton("Conferma");
				signUpButton.addActionListener(this);

				registrationPanel.add(nomeTField);
				registrationPanel.add(cognomeTField);
				registrationPanel.add(datePicker);
				registrationPanel.add(emailAddressTField);
				registrationPanel.add(signUpButton);
				registrationPanel.setVisible(true);
			}
			welcomePanel.add(registrationPanel);
			
			JPanel loginPanel = new JPanel(); 
			loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
			loginPanel.setBorder(new TitledBorder(new EtchedBorder(), "Log In"));
			{
				pswPField = new JPasswordField(15);
				signInButton = new JButton("Log in");
				signInButton.addActionListener(this);

				loginPanel.add(emailAddressTField);
				loginPanel.add(pswPField);
				loginPanel.add(signInButton);
				loginPanel.setVisible(true);
			}
			welcomePanel.add(loginPanel);
			welcomePanel.setVisible(true);
			this.add(welcomePanel);
		}
		
		
		profilePanel = new JPanel();
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.PAGE_AXIS));
		{
			JLabel profileLabel = new JLabel("Servizi registrati da " + nomeTField.getText() + " " + cognomeTField.getText());
			servicesComboBox = new JComboBox<IService>();
			//servicesComboBox.addActionListener(l);
			JPanel innerPanel = new JPanel(); 
			innerPanel.setLayout(new GridLayout(2, 2));
			{
				JLabel statusLabel = new JLabel("Status:");
				JTextField statusTField = new JTextField();
				toggleDisabledStatus = new JButton("Toggle Disabled Status");
				setWithdrawnStatus = new JButton("Set Withdrawn Status");
				
				//toggleDisabledStatus.addActionListener(l);
				//setWithdrawnStatus.addActionListener(l);
				
				innerPanel.add(statusLabel);
				innerPanel.add(statusTField);
				innerPanel.add(toggleDisabledStatus);
				innerPanel.add(setWithdrawnStatus);
			}
			profilePanel.add(innerPanel);
			profilePanel.setVisible(false);
			
			
			viewPastSConsents = new JButton("Visualizza tutti i permessi ritirati");
			//viewPastSConsents.addActionListener(l);
			
			//manca metà del pannello da aggiungere
		}
		
	}
	
	public void setModel (List<IService> serviziPerAccountUtente) {
		//servicesComboBox.removeActionListener(l);
		for (IService s : serviziPerAccountUtente)
			servicesComboBox.addItem(s);
		//servicesComboBox.addActionListener(l);
	}

	//da sistemare per intero, eventualmente prevedendo eventlistener per diversi tipi di oggetti e / o incapsulamento logica nel controller
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == signUpButton) {
			try {
				this.controller.createMyDataUser(nomeTField.getText().trim(), cognomeTField.getText().trim(), (Date) datePicker.getValue(), emailAddressTField.getText());
				// if creation of new user is safe, then
				welcomePanel.setVisible(false);
				profilePanel.setVisible(true);
				//updates list of services for new panel
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
				// reset values
				nomeTField.setText("Nome");
				cognomeTField.setText("Cognome");
				emailAddressTField.setText("email@gmail.com");
			} 
		} else if (e.getSource() == signInButton) {
			//check credentials
			//updates list of services for new panel
		}
	}
	
	
}
