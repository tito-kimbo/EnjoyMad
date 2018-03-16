package es.ucm.fdi.integration;

import java.util.List;

import es.ucm.fdi.data.ClubPOJO;

public interface ClubDAO {
	public List<ClubPOJO> getMatchingClubs(List<String> tags);
	public List<ClubPOJO> getMatchingClubs(int minPrice, int maxPrice);
	public List<ClubPOJO> getMatchingClubs(String location, int maxRange);
	public List<ClubPOJO> getClubs();
	public ClubPOJO getUser(String id);
}
