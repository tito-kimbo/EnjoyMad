package es.ucm.fdi.integration;

import java.util.List;

import es.ucm.fdi.integration.data.UserPOJO;

public interface UserDAO {
	/**
	 * Returns a user given an id, null if it's not found.
	 * @param id user's id
	 * @return the searched user; null otherwise
	 */
	public UserPOJO getUser(String id);
	
	/**
	 * Looks for a user (id) to see if it is registered or not.
	 * @param id the user's id
	 * @return if the user is registered
	 */
	public boolean exist(String id);

	/**
	 * Adds a new user to the user map.
	 * @param user user to be added
	 */
	public void addUser(UserPOJO user);

	/**
	 * Removes an existing user from the user map.
	 * @param id user's id to be removed
	 */
	public void removeuser(String id);
}
