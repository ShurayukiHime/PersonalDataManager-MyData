package persistence.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import persistence.IPersonalDataVault;
import persistence.IService;
import persistence.PersonalDataVault;
import persistence.consents.ConsentStatus;
import persistence.consents.DataConsent;
import persistence.consents.IConsent;
import persistence.consents.ServiceConsent;

class Account implements IAccount {

	private IService service;
	private IPersonalDataVault personalDataVault;
	private Map<ServiceConsent, List<DataConsent>> dataConsents = new HashMap<ServiceConsent, List<DataConsent>>();

	public Account(IService service, ServiceConsent firstConsent) {
		super();
		this.service = service;
		this.personalDataVault = new PersonalDataVault();
		// devo controllare che il primo consent sia attivo?
		this.dataConsents.put(firstConsent, new ArrayList<DataConsent>());
	}

	@Override
	public IService getService() {
		return this.service;
	}

	@Override
	public IPersonalDataVault getPersonalDataVault() {
		return personalDataVault;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((service == null) ? 0 : service.hashCode());
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
		Account other = (Account) obj;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.service.toString() + " con stato " + this.getActiveDisabledSC().getConsentStatus();
	}

	//ci può essere un solo consent attivo alla volta, e quindi (?) anche uno solo disattivo
	//returns null if no active consent has been issued
	@Override
	public ServiceConsent getActiveDisabledSC() {
		for (ServiceConsent sc : this.dataConsents.keySet()) {
			if (sc.getConsentStatus() != ConsentStatus.WITHDRAWN) {
				return sc;
			}
		}
		return null;
	}

	@Override
	public Set<ServiceConsent> getAllPastServiceConsents() {
		Set<ServiceConsent> result = new HashSet<ServiceConsent>();
		for (ServiceConsent sc : this.dataConsents.keySet())
			if (sc.getConsentStatus() == ConsentStatus.WITHDRAWN)
				result.add(sc);
		return result;
	}

}
