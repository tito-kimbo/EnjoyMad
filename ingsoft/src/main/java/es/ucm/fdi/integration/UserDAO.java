package es.ucm.fdi.integration;

import java.util.List;
import java.util.NoSuchElementException;

import es.ucm.fdi.integration.data.UserPOJO;

/**
 * This interface declares the methods a User data access object needs to implement.
 * @author Fco Borja 
 * @author Carlijn
 */
public interface UserDAO { 
	/**
	 * Returns the list of Users it contains.
	 * @return list of users
	 */
	public List<UserPOJO> getUsers();
	/**
	 * Returns the user instance which matches the identification.
	 * @param id identification of the user
	 * @return user that matches the id
	 * @throws NoSuchElementException if no element matches the identification
	 */
	public UserPOJO getUser(String id) throws NoSuchElementException;
	/**
	 * Inserts a user in the list.
	 * @param user User
	 */
	public void insert(UserPOJO user);
}
