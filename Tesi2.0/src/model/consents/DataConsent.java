package model.consents;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import model.registry.IMetadatum;

/**
 * This class represents the permission given to a service in order to obtain a
 * specific set of data at a specific time. For this purpose, it contains a Set
 * <IMetadatum>, a ServiceConsent, a timestamp and an UUID.
 * 
 * @author Giada
 *
 */

public class DataConsent implements IConsent {
	private Set<IMetadatum> metadata;
	private ServiceConsent serviceConsent;
	private Date timestamp;
	private UUID identifier;

	public DataConsent(Set<IMetadatum> metadata, ServiceConsent serviceConsent) {
		super();
		this.metadata = metadata;
		this.serviceConsent = serviceConsent;
		this.timestamp = new Date();
		this.identifier = UUID.randomUUID();
	}

	public UUID getIdentifier() {
		return identifier;
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
		DataConsent other = (DataConsent) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}

}
