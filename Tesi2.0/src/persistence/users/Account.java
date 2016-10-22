package persistence.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistence.IPersonalDataVault;
import persistence.IService;
import persistence.PersonalDataVault;
import persistence.consents.DataConsent;
import persistence.consents.IConsent;
import persistence.consents.ServiceConsent;

class Account implements IAccount {

	private IService service;
	private IPersonalDataVault personalDataVault;
	private Map<ServiceConsent, List<DataConsent>> dataConsents = new HashMap<ServiceConsent, List<DataConsent>>();
	// generica è meglio (?), ma si capisce dopo quello che rappresenta? i. e.
	// tutti i data consent associati ad un service consent? Questo generico mi
	// porta dentro dei cast...

	public Account(IService service, ServiceConsent firstConsent) {
		super();
		this.service = service;
		this.personalDataVault = new PersonalDataVault();
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

	public IConsent findActiveDisabledSConsent() {
		IConsent consent = null;
		for (IConsent sc : this.dataConsents.keySet()) {
			if (((ServiceConsent) sc).getTimestampWithdrawn() == null)
				consent = sc;
		}
		return consent;
	}

}
