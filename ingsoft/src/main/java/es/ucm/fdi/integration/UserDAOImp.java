package es.ucm.fdi.integration;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Implementation of <code>UserDAO</code> that works with a database.
 * 
 * @version 22.04.2018
 */
public class UserDAOImp implements UserDAO {
	private Map<String, UserPOJO> userMap;
	
	
	/**
	 * Constructor of the UserDAO. Sets the list of clubs empty.
	 */
	public UserDAOImp() {
		userMap = new HashMap<String, UserPOJO>();
	}
  
	/**
	 * @param stream get out a copy of the object of this DAO
	 * @throws IOException if I/O operations have an interruption
	 */
	synchronized private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeObject(userMap);
	}

	
	/**
	 * {@inheritDoc}
	 */
	synchronized public UserPOJO getUser(String id) {
		return userMap.get(id);
	}
	
  	/**
	 * {@inheritDoc}
	 */
	synchronized public boolean addUser(UserPOJO user) {
		userMap.put(user.getID(), user);
		return true;
	}
	
 	/**
	 * {@inheritDoc}
	 */
	synchronized public boolean exists(String id) {
		return userMap.containsKey(id);
	}
	
 	/**
	 * {@inheritDoc}
	 */
	public boolean removeUser(String id){
		if(exists(id)) {
			userMap.remove(id);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	synchronized public List<UserPOJO> getUsers() {
		List<UserPOJO> aux = new ArrayList<UserPOJO>(userMap.values());
		return aux;
	}
}
