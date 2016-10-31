package model.consents;

import java.util.Date;
import java.util.Set;

import model.registry.IMetadatum;

public class DataConsent implements IConsent {
	private Set<IMetadatum> metadata;
	private ServiceConsent serviceConsent;
	private Date timestamp;
	
	public DataConsent(Set<IMetadatum> metadata, ServiceConsent serviceConsent) {
		super();
		this.metadata = metadata;
		this.serviceConsent = serviceConsent;
		this.timestamp = new Date();
	}
	
}
