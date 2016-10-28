package model.consents;

import java.util.Date;

import model.services.IService;
import model.users.IUser;

public class ServiceConsent implements IConsent {
	private byte[] signedByService, signedByUser;
	private Date timestampGiven;
	private Date timestampWithdrawn;
	private IService service;
	private IUser user;
	private ConsentStatus consentStatus;

	public ServiceConsent(byte[] signedByService, byte[] signedByUser, Date timestamp, IService service, IUser user) {
		super();
		this.signedByService = signedByService;
		this.signedByUser = signedByUser;
		this.timestampGiven = timestamp;
		this.service = service;
		this.user = user;
		this.consentStatus = ConsentStatus.ACTIVE;
	}

	public Date getTimestampGiven() {
		return timestampGiven;
	}

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

	public IService getService() {
		return this.service;
	}

	public ConsentStatus getConsentStatus() {
		return this.consentStatus;
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
	 * @param newStatus
	 *            the new status of the consent
	 */
	protected void ChangeConsentStatus(ConsentStatus newStatus) {
		if (newStatus == ConsentStatus.WITHDRAWN)
			this.setTimestampWithdrawn(new Date());
		this.setConsentStatus(newStatus);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consentStatus == null) ? 0 : consentStatus.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result + ((timestampWithdrawn == null) ? 0 : timestampWithdrawn.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceConsent other = (ServiceConsent) obj;
		if (consentStatus != other.consentStatus)
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (timestampWithdrawn == null) {
			if (other.timestampWithdrawn != null)
				return false;
		} else if (!timestampWithdrawn.equals(other.timestampWithdrawn))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public String toString() {
		String result = "Service Consent issued at " + this.timestampGiven + " for user " + this.user.toString()
				+ " at service " + this.service.toString() + ". Current status: " + this.consentStatus;
		return this.consentStatus == ConsentStatus.WITHDRAWN ? result + " withdrawn at " + this.timestampWithdrawn + "."
				: result;
	}
}
