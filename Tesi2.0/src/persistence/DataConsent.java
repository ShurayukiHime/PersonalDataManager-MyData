package persistence;

import java.security.Signature;
import java.util.Date;

public class DataConsent extends ServiceConsent {
	//questa classe contiene qualcosa che permette di identificare i set di dati considerati (forse)

	public DataConsent(Signature userSignature, Date timestamp, String service) {
		super(userSignature, timestamp, service);
		// TODO Auto-generated constructor stub
	}

}
