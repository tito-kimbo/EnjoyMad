package es.ucm.fdi.integration;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.ClubPOJO;
/**
 * This class is a Club data access object that implements {@link ClubDAO}.
 * 
 * @version 22.04.2018
 */
public class ClubDAOImp implements ClubDAO {
	Map<String, ClubPOJO> clubMap;

	/**
	 * Constructor of the ClubDAO. Sets the list of clubs empty.
	 */
	public ClubDAOImp() {
		clubMap = new HashMap<String, ClubPOJO>();
	}

	/**
	 * {@inheritDoc}
	 */
	public ClubPOJO getClub(String id) {
		return clubMap.get(id);
	}
	
 	/**
	 * {@inheritDoc}
	 */
	public List<ClubPOJO> getClubs(){
		List<ClubPOJO> aux = new ArrayList<ClubPOJO>(clubMap.values());
		return aux;
	}

 	/**
	 * {@inheritDoc}
	 */
	public boolean exist(String id) {
		return clubMap.containsKey(id);
	}

 	/**
	 * {@inheritDoc}
	 */
	public void addClub(ClubPOJO club) {
		clubMap.put(club.getID(), club);
	}

  	/**
	 * {@inheritDoc}
	 */
	public void removeClub(String id) {
		clubMap.remove(id);
	}
}
