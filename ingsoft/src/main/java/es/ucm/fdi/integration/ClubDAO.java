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
	 * Returns the full map of <code>Clubs</code>.
	 * 
	 * @return List of <code>Clubs</code>.
	 */
	public List<ClubPOJO> getClubs();

	/**
	 * Looks for a <code>Club</code> (id) to see if it is registered or not.
	 * 
	 * @param id the <code>Club</code>'s id.
	 * @return if the <code>Club</code> is registered.
	 */
	public boolean exist(String id);

	/**
	 * Adds a new <code>Club</code> to the <code>Club</code> map.
	 * 
	 * @param club <code>Club</code> to be added.
	 */
	public void addClub(ClubPOJO club);

	/**
	 * Removes an existing <code>Club</code> from the <code>Club</code> map.
	 * 
	 * @param id <code>Club</code>'s id to be removed.
	 */
	public void removeClub(String id);
	
	/**
	 * Returns the club instance which matches the identification.
	 * @param id identification of the club
	 * @return club that matches the id
	 * @throws NoSuchElementException if no element matches the identification
	 */
	public ClubPOJO getClub(String id);
}
