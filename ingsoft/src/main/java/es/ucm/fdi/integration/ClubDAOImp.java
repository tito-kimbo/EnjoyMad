package es.ucm.fdi.integration;

import java.util.Map;
import java.util.HashMap;

import es.ucm.fdi.integration.data.ClubPOJO;

public class ClubDAOImp implements ClubDAO {
	Map<String, ClubPOJO> clubMap;

	public ClubDAOImp() {
		clubMap = new HashMap<String, ClubPOJO>();
	}

	@Override
	public ClubPOJO getClub(String id) {
		return clubMap.get(id);
	}

	@Override
	public boolean exist(String id) {
		return containsKey(id);
	}

	@Override
	public void addClub(ClubPOJO club) {
		clubMap.put(club.getID(), club);
	}

	@Override
	public void removeClub(String id) {
		clubMap.remove(id);
	}

	@Override
	public List<ClubPOJO> getMatchingClubs(int minPrice, int maxPrice) {
		// List of all registered clubs
		ArrayList<ClubPOJO> clubs = clubMap.values();
		// List of valid clubs (to be returned)
		List<ClubPOJO> validClubs = new ArrayList<ClubPOJO>();
		// Search
		for(ClubPOJO c : clubs) {
			if(c.getPrice() <= maxPrice && c.getPrice() >= minPrice) {
				list.add(c);
			}
		}			
				
		return validClubs;
	}

	/**
	 * TO BE IMPLEMENTED
	 */
	@Override
	public List<ClubPOJO> getMatchingClubs(List<String> tags) {
		return null;
	}

	/**
	 * TO BE IMPLEMENTED
	 */
	@Override
	public List<ClubPOJO> getMatchingClubs(String location, int maxRange){
		/*
		 * list := {}
		 * for each club in clubs
		 * 		Using Google Map's API get the distance from club to the location
		 * 		if distance is less or equal than maxRange add to list
		 * return list
		 */
		return null;
	}
}
