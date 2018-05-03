package es.ucm.fdi.integration;

import java.util.List;
import java.util.NoSuchElementException;

import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * This interface declares the methods a Club data access object needs to implement with database.
 * 
 * @version 03.05.2018
 * @author Carlijn

 */
public interface ClubDAOMySql {	
	

	/**
	 * Returns the full list of <code>Clubs</code>.
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
	 * Adds a new <code>Club</code> to the <code>Club</code> table in the DB.
	 * 
	 * @param club <code>Club</code> to be added.
	 * @return if the <code>Club</code> was added to the table in the DB.
	 */
	public boolean addClub(ClubPOJO club);

	/**
	 * Removes an existing <code>Club</code> from the <code>Clubs</code> table in the DB.
	 * 
	 * @param id <code>Club</code>'s id to be removed.
	 * @return if the <code>Club</code> was removed from the table in the DB.

	 */
	public boolean removeClub(String id);
	
	/**
	 * Returns the <code>Club</code> instance which matches the identification.
	 * @param id identification of the <code>Club</code>
	 * @return <code>Club</code> that matches the id
	 * @throws NoSuchElementException if no element matches the identification
	 */
	public ClubPOJO getClub(String id);
}
