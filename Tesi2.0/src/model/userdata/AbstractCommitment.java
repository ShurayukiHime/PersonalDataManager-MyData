package model.userdata;

import java.time.LocalDateTime;

import model.mapfeatures.Position;

public abstract class AbstractCommitment implements IDestination {
	
	public abstract String getDescription();
	public abstract LocalDateTime getDate();
	public abstract Position getPosition();

}
