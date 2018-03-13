package es.ucm.fdi.datos;

public interface ClubDAO {
	public List<ClubPOJO> getMatchingClubs(List<String> tags);
	public List<ClubPOJO> getMatchingClubs(int minPrice, int maxPrice);
	public List<ClubPOJO> getMatchingClubs(String location, int maxRange);
	public List<UserPOJO> getUsers();
	public ClubPOJO getUser(String id);
}
