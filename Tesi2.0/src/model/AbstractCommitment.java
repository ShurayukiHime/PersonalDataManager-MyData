package model;

import java.time.LocalDateTime;

public abstract class AbstractCommitment implements IDestination {
	
	public abstract String getDescription();
	public abstract LocalDateTime getDate();
	public abstract Position getPosition();

}
