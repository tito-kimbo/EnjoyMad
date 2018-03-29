package es.ucm.fdi.integration;

import java.util.Map;
import java.util.HashMap;

import es.ucm.fdi.integration.data.UserPOJO;

public class UserDAOImp implements UserDAO {
	Map<String, UserPOJO> userMap;

	public UserDAOImp() {
		userMap = new HashMap<String, UserPOJO>();
	}

	public UserPOJO getUser(String id) {
		return userMap.get(id);
	}
	
	public void addUser(UserPOJO user) {
		userMap.put(user.getID(), user);
	}
	
	public boolean exist(String id) {
		return userMap.containsKey(id);
	}
	
	public void removeUser(String id){
		
		if(exist(id)){
			userMap.remove(id);
		}else{
			//throw exception
		}
	}
}
