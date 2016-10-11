package model;

import java.util.ArrayList;
import java.util.List;

public class CalendarSuggestion implements ISuggestion {
	
	private List<AbstractCommitment> commitments;
	
	public CalendarSuggestion(List<AbstractCommitment> commitments) {
		if (commitments == null)
			throw new IllegalArgumentException("commitments must be initialized");
		this.commitments = commitments;
	}
	
	@Override
	public List<IDestination> getDestinations() {
		List<IDestination> result = new ArrayList<>();
		for (AbstractCommitment c : commitments) {
			result.add(c);
		}
		return result;
	}

	@Override
	public String getInfo() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Calendar Commitment:");
		sb.append('\n');
		int i=1;
		for (AbstractCommitment c : commitments) {
			sb.append(i + "] ");
			sb.append(c.getDescription());
			sb.append('\n');
			sb.append("date: ");
			sb.append(c.getDate());
			sb.append('\n');
			i++;
		}
		return sb.toString();
	}

}
