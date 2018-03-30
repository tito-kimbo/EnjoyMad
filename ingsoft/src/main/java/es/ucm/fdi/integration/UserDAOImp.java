package es.ucm.fdi.integration;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import es.ucm.fdi.integration.data.UserPOJO;

public class UserDAOImp implements UserDAO {
	List<UserPOJO> users;
	
	/**
	 * Constructor of the UserDAO. Sets the list of clubs empty.
	 */
	public UserDAOImp() {
		users = new ArrayList<UserPOJO>();
	}
	/**
	 * Returns the club that matches the identification in the positions of the list between the 'ini' position and the 'last' position.
	 * @param id identification
	 * @param ini initial position of the search
	 * @param last last position of the search
	 * @return user that matches the identification in the range
	 * @throws NoSuchElementException if there is no element matching the identification
	 */
	private int getUser(String id, int ini, int last) throws NoSuchElementException {
		if(ini == last)
			if(users.get(ini).getID() == id)
				return ini;
			else 
				throw new NoSuchElementException();
		int mid = (last+ini)/2;
		return (users.get(mid).getID().compareTo(id) < 0) ? getUser(id,mid+1,last) : getUser(id,ini,mid);
	}
	/**
	 * @inheritDoc
	 */
	public UserPOJO getUser(String id) {
		return users.get(getUser(id,0,users.size()-1));
	}
	/**
	 * @inheritDoc
	 */
	public void insert(UserPOJO user) {
		users.add(user);
	}
	/**
	 * @inheritDoc
	 */
	public List<UserPOJO> getUsers() {return users;}
}
