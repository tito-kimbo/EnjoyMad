package es.ucm.fdi.integration;

import java.util.List;
import java.util.NoSuchElementException;

import es.ucm.fdi.integration.data.UserPOJO;

/**
 * This interface declares the methods a User data access object needs to implement with database.
 * 
 * @version 03.05.2018
 * @author Carlijn

 */
public interface UserDAOMySql {	
	

	/**
	 * Returns the full list of <code>Users</code>.
	 * 
	 * @return List of <code>Users</code>.
	 */
  
	public List<UserPOJO> getUsers();

	/**
	 * Looks for a <code>User</code> (id) to see if it is registered or not.
	 * 
	 * @param id the <code>User</code>'s id.
	 * @return if the <code>User</code> is registered.
	 */
	public boolean exist(String id);

	/**
	 * Adds a new <code>User</code> to the <code>User</code> table in the DB.
	 * 
	 * @param user <code>User</code> to be added.
	 * @return if the <code>User</code> was added to the table in the DB.
	 */
	public boolean addUser(UserPOJO user);

	/**
	 * Removes an existing <code>User</code> from the <code>User</code> table in the DB.
	 * 
	 * @param id <code>User</code>'s id to be removed.
	 * @return if the <code>User</code> was removed from the table in the DB.

	 */
	public boolean removeUser(String id);
	
	/**
	 * Returns the <code>User</code> instance which matches the identification.
	 * @param id identification of the <code>User</code>
	 * @return <code>User</code> that matches the id
	 * @throws NoSuchElementException if no element matches the identification
	 */
	public UserPOJO getUser(String id);
}
