package es.ucm.fdi.datos;

import java.util.List;

public interface UserDAO {
	public UserPOJO getUser(String id);
	public void insert(UserPOJO user);
	public List<UserPOJO> getUsers();
}
