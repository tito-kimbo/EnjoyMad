package es.ucm.fdi.integration;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.ClubPOJO;

public class ClubDAOImp implements ClubDAO {
	Map<String, ClubPOJO> clubMap;

	public ClubDAOImp() {
		clubMap = new HashMap<String, ClubPOJO>();
	}

	public ClubPOJO getClub(String id) {
		return clubMap.get(id);
	}
	
	public List<ClubPOJO> getClubs(){
		List<ClubPOJO> aux = new ArrayList<ClubPOJO>(clubMap.values());
		return aux;
	}

	public boolean exist(String id) {
		return clubMap.containsKey(id);
	}

	public void addClub(ClubPOJO club) {
		clubMap.put(club.getID(), club);
	}

	public void removeClub(String id) {
		clubMap.remove(id);
	}
}
