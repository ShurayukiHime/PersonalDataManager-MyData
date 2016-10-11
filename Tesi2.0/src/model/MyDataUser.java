package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDataUser {
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String emailAddress;
	//private JWK key;
	//solo getter
	private List<Consent> consents;
	
	public MyDataUser() {
		super();
	}

	public MyDataUser(String firstName, String lastName, Date dateOfBirth, String emailAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.emailAddress = emailAddress;
		this.consents = new ArrayList<Consent>();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public List<Consent> getAllConsents() {
		return consents;
	}

	public void addConsent(Consent consent) {
		if (consent != null) {
			consents.add(consent);
		}
	}
	
	
}
