package es.ucm.fdi.integration;

import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.UserPOJO;

public class UserDAOImp implements UserDAO {
	Map<UserPOJO> userMap;

	public UserDAOImp() {
		userMap = new HashMap<String, UserPOJO>();
	}

	@Override
	public UserPOJO getUser(String id) {
		return userMap.get(id);
	}
	
	@Override
	public void addUser(UserPOJO user) {
		userMap.put(user.getID(), user);
	}

	@Override
	public boolean exist(String id) {
		return containsKey(id);
	}
}
