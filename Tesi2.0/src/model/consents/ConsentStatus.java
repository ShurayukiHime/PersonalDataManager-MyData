package model.consents;

public enum ConsentStatus {
	ACTIVE ("Active"),
	WITHDRAWN ("Withdrawn"),
	DISABLED ("Disabled");
	
	private String status;
	
	private ConsentStatus(String status) {
		this.status = status;
	}
	
	public String toString() {
		return this.status;
	}
}
