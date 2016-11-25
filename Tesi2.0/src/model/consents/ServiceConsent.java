package model.consents;

import java.util.Date;
import java.util.UUID;

import model.services.IService;
import model.users.IUser;

/**
 * This class represent the consent given by the user upon registering the
 * service. It ensures that both parties are reliable by storing a signed token
 * from both of them, along with the timestamp of its creation. The Status of
 * this consent can take one of the following values at a time: Active,
 * Disabled, Withdrawn. If the ConsentStatus is Active, it is possible for the
 * user to ask to be provided with the service registered, while it is not
 * possible to do so if the status is Withdrawn or Disabled. The difference
 * between a Disabled and a Withdrawn ConsentStatus relies in the fact that a
 * Disabled ConsentStatus can be switched back to Active, while it is not
 * possible to re-activate a Withdrawn ServiceConsent. In the latter case, a new
 * ServiceConsent has to be issued.
 * 
 * @author Giada
 *
 */

public class ServiceConsent implements IConsent {
	private byte[] signedByService, signedByUser;
	private Date timestampGiven;
	private Date timestampWithdrawn;
	private IService service;
	private IUser user;
	private ConsentStatus consentStatus;
	private UUID identifier;

	public ServiceConsent(byte[] signedByService, byte[] signedByUser, Date timestamp, IService service, IUser user) {
		super();
		this.signedByService = signedByService;
		this.signedByUser = signedByUser;
		this.timestampGiven = timestamp;
		this.service = service;
		this.user = user;
		this.consentStatus = ConsentStatus.ACTIVE;
		this.identifier = UUID.randomUUID();
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

	public IUser getUser() {
		return user;
	}

	private void setConsentStatus(ConsentStatus consentStatus) {
		this.consentStatus = consentStatus;
	}

	/**
	 * This function allows changing the Consent Status value to Active,
	 * Disabled or Withdrawn. In case of withdrawal of consent, it is necessary
	 * to update the corresponding timestampWithdrawn. The method which invokes
	 * this function should check that only valid changes are made: for example,
	 * that no withdrawn Consent becomes Active again, or that the Consent isn't
	 * already Withdrawn. This is why no other checks are made.
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
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
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
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}

	public String toString() {
		String result = "Service Consent " + this.identifier + " issued at " + this.timestampGiven + " for user "
				+ this.user.toString() + " at service " + this.service.toString() + ". Current status: "
				+ this.consentStatus;
		return this.consentStatus == ConsentStatus.WITHDRAWN ? result + " at " + this.timestampWithdrawn + "." : result;
	}
}
