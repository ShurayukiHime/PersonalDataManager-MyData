package persistence;

import java.security.Signature;
import java.util.Date;

public class ServiceConsent implements IConsent {
	// la firma del servizio?
	private Signature userSignature;
	private Date timestampGiven;
	private Date timestampWithdrawn;
	private String service;
	private ConsentStatus consentStatus;

	public ServiceConsent(Signature userSignature, Date timestamp, String service) {
		super();
		this.userSignature = userSignature;
		this.timestampGiven = timestamp;
		this.service = service;
		this.consentStatus = ConsentStatus.ACTIVE;
	}

	public Signature getUserSignature() {
		return userSignature;
	}

	public Date getTimestampGiven() {
		return timestampGiven;
	}

	// CAMBIARE con identificazione servizi
	// generare equals che tiene conto di questa cosa
	public Date getTimestampWithdrawn() {
		if (this.timestampWithdrawn != null)
			return timestampWithdrawn;
		else {
			System.out.println("Consent for the service " + this.service + " is  still Active or Disabled");
			return null;
		}
	}

	private void setTimestampWithdrawn(Date timestampWithdrawn) {
		this.timestampWithdrawn = timestampWithdrawn;
	}

	public String getService() {
		return service;
	}

	public ConsentStatus getConsentStatus() {
		return consentStatus;
	}

	private void setConsentStatus(ConsentStatus consentStatus) {
		this.consentStatus = consentStatus;
	}

	/**
	 * This function allows changing the Consent Status value to Active,
	 * Disabled or Withdrawn. In case of withdrawal of consent, it is necessary
	 * to update the corresponding timestampWithdrawn. The method which invokes
	 * this function should check that only valid changes are made: for example,
	 * that no withdrawn Consent becomes Active again.
	 * 
	 * @param newStatus the new status of the consent
	 */
	protected void ChangeConsentStatus(ConsentStatus newStatus) {
		if (newStatus == ConsentStatus.WITHDRAWN)
			this.setTimestampWithdrawn(new Date());
		this.setConsentStatus(newStatus);

	}
}
