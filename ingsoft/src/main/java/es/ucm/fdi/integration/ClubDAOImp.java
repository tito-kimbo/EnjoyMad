package es.ucm.fdi.integration;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
	private Map<String, ClubPOJO> clubMap;

	
	/**
	 * Constructor of the ClubDAO. Sets the list of clubs empty.
	 */
	public ClubDAOImp() {
		clubMap = new HashMap<String, ClubPOJO>();
	}
	
	/**
	 * @param stream get out a copy of the object of this DAO
	 * @throws IOException if I/O operations have an interruption
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeObject(clubMap);
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
