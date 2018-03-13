package es.ucm.fdi.datos;

public interface UserDAO {
	public UserPOJO getUser(String id);
	public void insert(UserPOJO user);
	public List<UserPOJO> getUsers();
}
