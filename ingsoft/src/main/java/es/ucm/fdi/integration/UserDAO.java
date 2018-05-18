package es.ucm.fdi.integration;

import java.util.List;

import es.ucm.fdi.integration.data.UserPOJO;

/**
 * This interface declares the methods a User data access object needs to implement.
 * 
 * @version 22.04.2018
 */
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
	public boolean exists(String id);

	/**
	 * Adds a new user to the user map.
	 * @param user user to be added
	 */
	public boolean addUser(UserPOJO user);

	/**
	 * Removes an existing user from the user map.
	 * @param id user's id to be removed
	 * @return 
	 */
	public boolean removeUser(String id);
	
	/**
	 * Retrieves the full list of <code>Users</code>
	 * @return the list of <code>Users</code>>
	 */
	public List<UserPOJO> getUsers();

}
