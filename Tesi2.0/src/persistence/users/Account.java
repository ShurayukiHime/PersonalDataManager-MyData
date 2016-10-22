package persistence.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistence.DataConsent;
import persistence.IConsent;
import persistence.IService;
import persistence.ServiceConsent;

public class Account implements IAccount {

	private IService service;
	private Map<IConsent, List<IConsent>> dataConsents = new HashMap<IConsent, List<IConsent>>();
	// generica è meglio (?), ma si capisce dopo quello che rappresenta? i. e.
	// tutti i data consent associati ad un service consent? Questo generico mi
	// porta dentro dei cast...

	public Account(IService service, ServiceConsent firstConsent) {
		super();
		this.service = service;
		this.dataConsents.put(firstConsent, new ArrayList<IConsent>());
	}

	@Override
	public IService getService() {
		return this.service;
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
