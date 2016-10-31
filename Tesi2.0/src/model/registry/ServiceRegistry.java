package model.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.services.IService;

public class ServiceRegistry {
	
	private static Map<IService, Set<IMetadatum>> mappings;
	
	private ServiceRegistry () {
		this.mappings = new HashMap<IService, Set<IMetadatum>>();
	}
	
	public static void registerService (IService service, Set<IMetadatum> metadata) {
		//forse dovrei verificare che il servizio sia affidabile, ma vabbè
		if (service == null || metadata == null)
			throw new IllegalArgumentException("Service and corresponding metadata must not be null.");
		mappings.put(service, metadata);
	}
	
	public static Set<IMetadatum> getMetadataForService (IService service) {
		if (service == null)
			throw new IllegalArgumentException("Cannot retrieve metadata for null Service.");
		if (!mappings.containsKey(service))
			throw new IllegalArgumentException("Service " + service.toString() + " entry is not registered in Service Registry.");
		return mappings.get(service);
	}
	
}
