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
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import persistence.IService;

public class MyDataProfile extends JFrame {

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

	private JButton toggleDisabledStatusButton;
	private JButton setWithdrawnStatusButton;
	private JButton viewPastSConsentsButton;
	private JButton addServiceButton;
	private JButton requestServiceButton;

	public MyDataProfile(Controller controller) {
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
				emailAddressTField = new JTextField(15);
				emailAddressTField.setText("email@gmail.com");
				emailAddressTField.setEditable(true);

				signUpButton = new JButton("Conferma");
				signUpButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						signUpButtonClicked();
					}
				});

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
				emailAddressTField = new JTextField(15);
				emailAddressTField.setText("email@gmail.com");
				emailAddressTField.setEditable(true);
				pswPField = new JPasswordField(15);
				signInButton = new JButton("Log in");
				signInButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						signInButtonClicked();
					}
				});

				loginPanel.add(emailAddressTField);
				loginPanel.add(pswPField);
				loginPanel.add(signInButton);
				loginPanel.setVisible(true);
			}
			welcomePanel.add(loginPanel);
			welcomePanel.setVisible(true);
			this.add(welcomePanel);
			this.pack();
		}

	}

	private void showProfile() {
		welcomePanel.setVisible(false);
		profilePanel = new JPanel();
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.PAGE_AXIS));
		{
			JLabel profileLabel = new JLabel(
					"Servizi registrati da " + nomeTField.getText() + " " + cognomeTField.getText());
			servicesComboBox = new JComboBox<IService>();
			// servicesComboBox.addActionListener(l);

			profilePanel.add(profileLabel);
			profilePanel.add(servicesComboBox);

			JPanel statusPanel = new JPanel();
			statusPanel.setLayout(new GridLayout(2, 2));
			{
				JLabel statusLabel = new JLabel("Status:");
				JTextField statusTField = new JTextField();
				statusTField.setEditable(false);
				toggleDisabledStatusButton = new JButton("Toggle Disabled Status");
				setWithdrawnStatusButton = new JButton("Set Withdrawn Status");

				toggleDisabledStatusButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						toggleDisabledStatusButtonClicked();

					}
				});
				setWithdrawnStatusButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						setWithdrawnStatusButtonClicked();

					}
				});

				statusPanel.add(statusLabel);
				statusPanel.add(statusTField);
				statusPanel.add(toggleDisabledStatusButton);
				statusPanel.add(setWithdrawnStatusButton);
				statusPanel.setVisible(true);
			}
			profilePanel.add(statusPanel);

			JPanel centralButtonsPanel = new JPanel();
			centralButtonsPanel.setLayout(new GridLayout(3, 1));
			{
				requestServiceButton = new JButton("Richiedi Servizio");
				requestServiceButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						requestServiceButtonClicked();

					}
				});
				addServiceButton = new JButton("Aggiungi un nuovo servizio");
				addServiceButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						callServiceRegistry();

					}
				});
				viewPastSConsentsButton = new JButton("Visualizza tutti i permessi ritirati");
				viewPastSConsentsButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						viewPastSConsentsButton();

					}
				});

				centralButtonsPanel.add(requestServiceButton);
				centralButtonsPanel.add(addServiceButton);
				centralButtonsPanel.add(viewPastSConsentsButton);
			}
			profilePanel.add(centralButtonsPanel, BoxLayout.PAGE_AXIS);
			centralButtonsPanel.setVisible(true);

			JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(new GridLayout(1, 1));
			{
				JTextArea pastServicesConsent = new JTextArea(10, 30);
				pastServicesConsent.setEditable(false);
				pastServicesConsent.setLineWrap(true);
				JScrollPane scrollPane = new JScrollPane(pastServicesConsent);

				bottomPanel.add(scrollPane);
			}
			profilePanel.add(bottomPanel, BoxLayout.PAGE_AXIS);
			bottomPanel.setVisible(true);
		}
		profilePanel.setVisible(true);
		this.add(profilePanel);
		this.pack();
	}

	public void setModel(List<IService> serviziPerAccountUtente) {
		// servicesComboBox.removeActionListener(l);
		for (IService s : serviziPerAccountUtente)
			servicesComboBox.addItem(s);
		// servicesComboBox.addActionListener(l);
	}

	// da sistemare per intero, eventualmente prevedendo eventlistener per
	// diversi tipi di oggetti e / o incapsulamento logica nel controller
	private void signUpButtonClicked() {
		try {
			this.controller.createMyDataUser(nomeTField.getText().trim(), cognomeTField.getText().trim(),
					(Date) datePicker.getValue(), emailAddressTField.getText());
			// if creation of new user is safe, then
			showProfile();
			// updates list of services for new panel
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
			// reset values
			nomeTField.setText("Nome");
			cognomeTField.setText("Cognome");
			emailAddressTField.setText("email@gmail.com");
		}
	}

	private void signInButtonClicked() {
		// check credentials
		// updates list of services for new panel
	}

	private void toggleDisabledStatusButtonClicked() {
		// retrieve il servizio selezionato
		// chiede al controller di fare il toggle per il servizio, utente
		// invoca un aggiornamento della combo box
	}

	private void setWithdrawnStatusButtonClicked() {
		// retrieve il servizio selezionato
		// chiede al controller di fare withdraw dello stato per il servizio,
		// utente
		// invoca aggiornamento combo box
	}

	private void requestServiceButtonClicked() {
		// mostra la schermata del servizio di nicola
		// dove?
	}

	private void callServiceRegistry() {
		JOptionPane.showMessageDialog(null, "Sto invocando il Service Registry...", "Info:",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void viewPastSConsentsButton() {
		// retrieve service selected
		// ask controller for sconsents
		// show content in text area
	}
}
