package es.ucm.fdi.integration;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import es.ucm.fdi.integration.data.ClubPOJO;
/**
 * This class is a Club data access object that implements {@link ClubDAO}.
 * 
 * @version 22.04.2018
 */
public class ClubDAOImp implements ClubDAO {
	//Thread safe Map
	private ConcurrentMap<String, ClubPOJO> clubMap;

	
	/**
	 * Constructor of the ClubDAO. Sets the list of clubs empty.
	 */
	public ClubDAOImp() {
		clubMap = new ConcurrentHashMap<String, ClubPOJO>();
	}
	
	/**
	 * @param stream get out a copy of the object of this DAO
	 * @throws IOException if I/O operations have an interruption
	 */
	synchronized private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeObject(clubMap);
	}
	

	/**
	 * {@inheritDoc}
	 */
	synchronized public ClubPOJO getClub(String id) {
		return clubMap.get(id);
	}
	
 	/**
	 * {@inheritDoc}
	 */
	synchronized public List<ClubPOJO> getClubs(){
		List<ClubPOJO> aux = new ArrayList<ClubPOJO>(clubMap.values());
		return aux;
	}

 	/**
	 * {@inheritDoc}
	 */
	synchronized public boolean exists(String id) {
		return clubMap.containsKey(id);
	}

 	/**
	 * {@inheritDoc}
	 */
	synchronized public boolean addClub(ClubPOJO club) {
		clubMap.put(club.getID(), club);
		return true;
	}

  	/**
	 * {@inheritDoc}
	 */
	synchronized public boolean removeClub(String id) {
		clubMap.remove(id);
		return true;
	}
}
