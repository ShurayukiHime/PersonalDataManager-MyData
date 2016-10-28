package model;

import java.util.List;

import model.security.ISecurityManager;
import model.userdata.suggestions.ISuggestion;

public interface IService {
	public ISecurityManager getSecurityManager();
	public boolean equals (Object obj);
	public List<ISuggestion> calculateMostLikelyNextTrip();
}
