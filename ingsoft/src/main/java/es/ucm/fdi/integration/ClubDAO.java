package es.ucm.fdi.integration;

import java.util.List;
import java.util.NoSuchElementException;

import es.ucm.fdi.integration.data.ClubPOJO;
/**
 * This interface declares the methods a Club data access object needs to implement.
 * @author Fco Borja 
 * @author Carlijn
 */
public interface ClubDAO {
	/**
	 * Returns the list of clubs it contains.
	 * @return list of clubs
	 */
	public List<ClubPOJO> getClubs();
	/**
	 * Returns the club instance which matches the identification.
	 * @param id identification of the club
	 * @return club that matches the id
	 * @throws NoSuchElementException if no element matches the identification
	 */
	public ClubPOJO getClub(String id) throws NoSuchElementException;
	/**
	 * Inserts a club in the list.
	 * @param club club
	 */
	public void insert(ClubPOJO club);
}
