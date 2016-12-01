 package gui;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import model.services.IService;

public class MyDataProfile extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private IController controller;

	private JPanel welcomePanel;
	private JTextField nomeTField;
	private JTextField cognomeTField;
	private JSpinner datePicker;
	private JTextField emailAddressTField;
	private JPasswordField signUpPField;
	private JPasswordField signInPField;

	private JButton signInButton;
	private JButton signUpButton;

	private JPanel profilePanel;
	private JComboBox<IService> servicesComboBox;
	private JTextArea logTextArea;

	private JTextComponent statusTField;
	private JCheckBox toggleDisabledStatusCheckBox;
	private JButton setWithdrawnStatusButton;
	private JButton newServiceConsentButton;
	private JButton viewDataConsentsButton;
	private JButton addServiceButton;
	private JButton requestServiceButton;

	public MyDataProfile(IController controller) {
		super("Welcome to MyData");
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
				nomeTField.setText("John");
				cognomeTField = new JTextField(15);
				cognomeTField.setText("Doe");
				datePicker = new JSpinner(new SpinnerDateModel());
				JSpinner.DateEditor dateDEditor = new JSpinner.DateEditor(datePicker, "dd/MM/yyyy");
				datePicker.setEditor(dateDEditor);
				emailAddressTField = new JTextField(15);
				emailAddressTField.setText("john.doe@gmail.com");
				signUpPField = new JPasswordField(15);

				signUpButton = new JButton("Sign Up");
				signUpButton.setAlignmentX(CENTER_ALIGNMENT);
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
				registrationPanel.add(signUpPField);
				registrationPanel.add(signUpButton);
				registrationPanel.setVisible(true);
			}
			welcomePanel.add(registrationPanel);

			JPanel loginPanel = new JPanel();
			loginPanel.setLayout(new GridBagLayout());
			loginPanel.setBorder(new TitledBorder(new EtchedBorder(), "Log In"));
			JPanel utilityPanel = new JPanel();
			utilityPanel.setLayout(new BoxLayout(utilityPanel, BoxLayout.PAGE_AXIS));
			{
				emailAddressTField = new JTextField(15);
				emailAddressTField.setMaximumSize(emailAddressTField.getPreferredSize());
				emailAddressTField.setText("john.doe@gmail.com");
				emailAddressTField.setEditable(true);
				signInPField = new JPasswordField(15);
				signInPField.setMaximumSize(signInPField.getPreferredSize());
				
				signInButton = new JButton("Log in");
				signInButton.setAlignmentX(CENTER_ALIGNMENT);
				signInButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						signInButtonClicked();
					}
				});

				utilityPanel.add(emailAddressTField);
				utilityPanel.add(signInPField);
				utilityPanel.add(signInButton);
				utilityPanel.setVisible(true);
			}
			loginPanel.add(utilityPanel);
			loginPanel.setVisible(true);
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
		profilePanel.setBorder(new TitledBorder(new EtchedBorder(), "Your Profile"));
		{
			JLabel profileLabel = new JLabel(
					"Services registered by " + nomeTField.getText() + " " + cognomeTField.getText());
			servicesComboBox = new JComboBox<IService>();
			servicesComboBox.setEditable(false);
			servicesComboBox.addActionListener(this);

			profilePanel.add(profileLabel);
			profilePanel.add(servicesComboBox);

			JPanel statusPanel = new JPanel();
			statusPanel.setLayout(new GridLayout(3, 2));
			{
				JLabel statusLabel = new JLabel("Status:");
				statusTField = new JTextField();
				statusTField.setEditable(false);
				toggleDisabledStatusCheckBox = new JCheckBox("Toggle Disabled Status");
				setWithdrawnStatusButton = new JButton("Set Withdrawn Status");
				newServiceConsentButton = new JButton("Issue new Consent");
				newServiceConsentButton.setEnabled(false);
				viewDataConsentsButton = new JButton("View past Data Consents");

				toggleDisabledStatusCheckBox.addActionListener(new ActionListener() {

					// per convenzione, if cb is selected -> active
					// else -> disabled
					@Override
					public void actionPerformed(ActionEvent e) {
						toggleDisabledStatusCheckBoxClicked();
					}
				});
				setWithdrawnStatusButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						setWithdrawnStatusButtonClicked();
					}
				});
				newServiceConsentButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						newServiceConsentButtonClicked();

					}
				});
				viewDataConsentsButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						viewDataConsentsButtonClicked();
					}
				});
				
				statusPanel.add(statusLabel);
				statusPanel.add(statusTField);
				statusPanel.add(toggleDisabledStatusCheckBox);
				statusPanel.add(setWithdrawnStatusButton);
				statusPanel.add(newServiceConsentButton);
				statusPanel.add(viewDataConsentsButton);
				statusPanel.setVisible(true);
			}
			profilePanel.add(statusPanel);

			JPanel centralButtonsPanel = new JPanel();
			centralButtonsPanel.setLayout(new GridLayout(2, 1));
			{
				
				requestServiceButton = new JButton("Request Service");
				addServiceButton = new JButton("Add a new Service");
				
				requestServiceButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						requestServiceButtonClicked();
					}
				});
				addServiceButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						callServiceRegistry();
					}
				});

				centralButtonsPanel.add(requestServiceButton);
				centralButtonsPanel.add(addServiceButton);
			}
			profilePanel.add(centralButtonsPanel, BoxLayout.PAGE_AXIS);
			centralButtonsPanel.setVisible(true);

			JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(new GridLayout(1, 1));
			{
				logTextArea = new JTextArea(10, 30);
				logTextArea.setEditable(false);
				logTextArea.setLineWrap(true);
				JScrollPane scrollPane = new JScrollPane(logTextArea);

				bottomPanel.add(scrollPane);
			}
			profilePanel.add(bottomPanel, BoxLayout.PAGE_AXIS);
			bottomPanel.setVisible(true);
		}
		this.popolaServicesComboBox();
		this.add(profilePanel);
		this.profilePanel.setVisible(true);
		this.pack();
	}

	private void popolaServicesComboBox() {
		List<IService> activeServices = this.controller.getAllActiveServicesForUser();
		servicesComboBox.removeActionListener(this);
		servicesComboBox.removeAllItems();
		for (IService s : activeServices)
			servicesComboBox.addItem(s);
		servicesComboBox.addActionListener(this);
		boolean buttonsEnabled = false;
		if (!(activeServices.isEmpty())) {
			buttonsEnabled = true;
		}
		this.toggleDisabledStatusCheckBox.setEnabled(buttonsEnabled);
		this.setWithdrawnStatusButton.setEnabled(buttonsEnabled);
		this.requestServiceButton.setEnabled(buttonsEnabled);
		
	}

	private void signUpButtonClicked() {
		try {
			this.controller.createMyDataUser(nomeTField.getText().trim(), cognomeTField.getText().trim(),
					(Date) datePicker.getValue(), emailAddressTField.getText(), signUpPField.getPassword());
			// if creation of new user is safe, then
			showProfile();
		} catch (IllegalStateException | IllegalArgumentException | SecurityException e1) {
			e1.printStackTrace();
			this.controller.getUserInteractor().showErrorMessage(e1.getMessage());
			// reset values
			nomeTField.setText("Nome");
			cognomeTField.setText("Cognome");
			emailAddressTField.setText("email@gmail.com");
		}
	}

	private void signInButtonClicked() {
		try {
			this.controller.logInUser(emailAddressTField.getText(), signInPField.getPassword());
			// if user authenticated,
			showProfile();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			this.controller.getUserInteractor().showErrorMessage(e1.getMessage());
			emailAddressTField.setText("email@gmail.com");
		}
	}

	private void toggleDisabledStatusCheckBoxClicked() {
		IService selectedService = servicesComboBox.getItemAt(servicesComboBox.getSelectedIndex());
		// passo solo solo il servizio, perchè questo bottone è cliccabile solo
		// se l'utente è autenticato
		try {
			this.controller.toggleStatus(selectedService, this.toggleDisabledStatusCheckBox.isSelected());
			boolean status = this.toggleDisabledStatusCheckBox.isSelected();
			this.updateStatusTextField(status);
			this.requestServiceButton.setEnabled(status);
			this.viewPastSConsents();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			this.controller.getUserInteractor().showErrorMessage(e.getMessage());
		}
	}

	private void setWithdrawnStatusButtonClicked() {
		IService selectedService = servicesComboBox.getItemAt(servicesComboBox.getSelectedIndex());
		// passo solo solo il servizio, perchè questo bottone è cliccabile solo
		// se l'utente è autenticato
		this.controller.withdrawConsentForService(selectedService);
		// non faccio controlli sulle eccezioni perchè in teoria dovrebbe andare
		// tutto bene
		this.statusTField.setText("Withdrawn");
		this.viewPastSConsents();
		this.newServiceConsentButton.setEnabled(true);
	}

	private void requestServiceButtonClicked() {
		IService selectedService = servicesComboBox.getItemAt(servicesComboBox.getSelectedIndex());
		this.controller.getServicePanel(selectedService).setVisible(true);
	}

	private void newServiceConsentButtonClicked() {
		IService selectedService = servicesComboBox.getItemAt(servicesComboBox.getSelectedIndex());
		this.controller.addNewServiceConsent(selectedService);
		this.newServiceConsentButton.setEnabled(false);
		this.actionPerformed(null);
	}

	private void viewDataConsentsButtonClicked() {
		DataConsentFrame dcFrame = new DataConsentFrame(this.controller);
		IService selectedService = servicesComboBox.getItemAt(servicesComboBox.getSelectedIndex());
		dcFrame.updateTextArea(this.controller.getAllDConsents(selectedService));
		dcFrame.setVisible(true);
	}
	
	private void callServiceRegistry() {
		this.controller.getUserInteractor().showInfoMessage("Sto invocando il Service Registry... (forse)");
		// barbatrucco per aggiungere MLNT la prima volta
		if (this.servicesComboBox.getItemCount() == 0)
			try {
				this.controller.addService(null);
			} catch (SecurityException e) {
				e.printStackTrace();
				this.controller.getUserInteractor().showErrorMessage(e.getMessage());
			}
		this.popolaServicesComboBox();
		this.actionPerformed(null);
	}

	private void viewPastSConsents() {
		IService selectedService = servicesComboBox.getItemAt(servicesComboBox.getSelectedIndex());
		this.logTextArea.setText(this.controller.getAllSConsents(selectedService));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IService selectedService = servicesComboBox.getItemAt(servicesComboBox.getSelectedIndex());
		// segue la convenzione true -> active, false -> disabled;
		boolean status;
		try {
			status = this.controller.getADStatusForService(selectedService);
			toggleDisabledStatusCheckBox.setSelected(status);
			this.updateStatusTextField(status);
			this.viewPastSConsents();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			this.controller.getUserInteractor().showErrorMessage(e1.getMessage());
		}
		this.popolaServicesComboBox();
	}

	private void updateStatusTextField(boolean status) {
		if (status)
			this.statusTField.setText("Active");
		else
			this.statusTField.setText("Disabled");
	}
}
