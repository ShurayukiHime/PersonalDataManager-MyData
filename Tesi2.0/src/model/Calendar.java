package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Calendar implements ICalendar {
	
	private List<AbstractCommitment> commitments;
	
	public Calendar (List<AbstractCommitment> commitments) {
		if (commitments == null)
			throw new IllegalArgumentException("commitments must be initialized");
		this.commitments = commitments;
	}

	@Override
	public List<AbstractCommitment> getCommitmentByDate(LocalDateTime date) {
		if (date == null)
			throw new IllegalArgumentException("date must be initialized");
		List<AbstractCommitment> result = new ArrayList<>();
		
		for (AbstractCommitment c : commitments) {
			if (Math.abs(ChronoUnit.MINUTES.between(c.getDate(), date)) <= 30)
				result.add(c);
		}
		return result;
	}

}
