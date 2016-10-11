package model;

import java.time.LocalDateTime;
import java.util.List;

public interface ICalendar {
	
	public List<AbstractCommitment> getCommitmentByDate (LocalDateTime date);

}
