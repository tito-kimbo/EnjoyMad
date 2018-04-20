package es.ucm.fdi.integration;

import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.UserPOJO;

public class UserDAOImp implements UserDAO {
	private Map<String, UserPOJO> userMap;
	
	/**
	 * Constructor of the UserDAO. Sets the list of clubs empty.
	 */
	public UserDAOImp() {
		userMap = new HashMap<String, UserPOJO>();
	}
  
	/**
	 * {@inheritDoc}
	 */
	public UserPOJO getUser(String id) {
		return userMap.get(id);
	}
	
  	/**
	 * {@inheritDoc}
	 */
	public void addUser(UserPOJO user) {
		userMap.put(user.getID(), user);
	}
	
 	/**
	 * {@inheritDoc}
	 */
	public boolean exist(String id) {
		return userMap.containsKey(id);
	}
	
 	/**
	 * {@inheritDoc}
	 */
	public void removeUser(String id){
		if(exist(id)) {
			userMap.remove(id);
		} else {
			//throw exception
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<UserPOJO> getUsers() {
		List<UserPOJO> aux = new ArrayList<UserPOJO>(userMap.values());
		return aux;
	}
}
