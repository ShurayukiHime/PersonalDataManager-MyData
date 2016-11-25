package model.consents;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * This class represents the permission given to a service in order to obtain a
 * specific set of data at a specific time. For this purpose, it contains a Set
 * <String>, a ServiceConsent, a timestamp and an UUID.
 * 
 * @author Giada
 *
 */

public class DataConsent implements IConsent {
	private Set<String> typesConst;
	private ServiceConsent serviceConsent;
	private Date timestamp;
	private UUID identifier;

	protected DataConsent(Set<String> typesConst, ServiceConsent serviceConsent) {
		super();
		this.typesConst = typesConst;
		this.serviceConsent = serviceConsent;
		this.timestamp = new Date();
		this.identifier = UUID.randomUUID();
	}

	public Set<String> getTypesConst() {
		return typesConst;
	}

	public ServiceConsent getServiceConsent() {
		return serviceConsent;
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

	@Override
	public String toString() {
		return "DataConsent " + this.identifier + " issued at " + this.timestamp;
	}

}
