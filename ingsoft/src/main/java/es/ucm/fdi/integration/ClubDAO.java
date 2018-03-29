package es.ucm.fdi.integration;

import java.util.List;

import es.ucm.fdi.integration.data.ClubPOJO;

public interface ClubDAO {
	/**
	 * Returns a club given an id, null if it's not found.
	 * @param id club's id
	 * @return the searched club; null otherwise
	 */
	public clubPOJO getClub(String id);

	/**
	 * Looks for a club (id) to see if it is registered or not.
	 * @param id the club's id
	 * @return if the club is registered
	 */
	public boolean exist(String id);

	/**
	 * Adds a new club to the club map.
	 * @param club club to be added
	 */
	public void addClub(ClubPOJO club);

	/**
	 * Removes an existing club from the club map.
	 * @param id club's id to be removed
	 */
	public void removeClub(String id);
	
	/**
	 * TO BE IMPLEMENTED
	 */
	public List<clubPOJO> getMatchingClubs(List<String> tags);

	/**
	 * Returns a list of valid clubs within a range of ticket price.
	 * @param minPrice minimum price admitted
	 * @param maxPrice maximum price admitted
	 * @return list of valid clubs 
	 */
	public List<clubPOJO> getMatchingClubs(int minPrice, int maxPrice);

	/**
	 * TO BE IMPLEMENTED
	 */
	public List<clubPOJO> getMatchingClubs(String location, int maxRange);
	
}
