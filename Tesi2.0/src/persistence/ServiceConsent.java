package persistence;

import java.security.Signature;
import java.util.Date;

public class ServiceConsent {
	private Signature userSignature;
	private Date timestamp;
	private String service;
	private ConsentStatus consentStatus;
	
	public ServiceConsent(Signature userSignature, Date timestamp, String service) {
		super();
		this.userSignature = userSignature;
		this.timestamp = timestamp;
		this.service = service;
		this.consentStatus = ConsentStatus.ACTIVE;
	}
	
	public void DisableConsent() {
		//TODO
	}
	
	public void WithdrawConsent() {
		//TODO
	}
}
