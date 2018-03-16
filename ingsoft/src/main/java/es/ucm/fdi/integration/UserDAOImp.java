package es.ucm.fdi.integration;

import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.UserPOJO;

public class UserDAOImp implements UserDAO {
	List<UserPOJO> users;
	
	private int getUser(String id, int ini, int last) {
		if(ini == last)
			return ini;
		int mid = (last + ini)/2;
		return (users.get(mid).getId().compareTo(id) < 0) ? getUser(id,mid+1,last) : getUser(id,ini,mid);
	}
	public UserDAOImp() {
		users = new ArrayList<UserPOJO>();
	}
	public UserPOJO getUser(String id) {
		return users.get(getUser(id,0,users.size()-1));
	}
	public void insert(UserPOJO user) {
		//users.add(user,getUser(user.getId(),0,users.size()-1));
		users.add(user);
	}
	public List<UserPOJO> getUsers() {return users;}
}
