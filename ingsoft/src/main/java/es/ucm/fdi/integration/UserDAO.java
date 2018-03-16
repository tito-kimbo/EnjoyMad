package es.ucm.fdi.integration;

import java.util.List;

import es.ucm.fdi.data.UserPOJO;

public interface UserDAO {
	public UserPOJO getUser(String id);
	public void insert(UserPOJO user);
	public List<UserPOJO> getUsers();
}
