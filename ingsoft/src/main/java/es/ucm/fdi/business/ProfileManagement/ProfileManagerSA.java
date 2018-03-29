package es.ucm.fdi.business.ProfileManagement;

/**
 * Interface defining the functionalities to be used in 
 * profile management.
 * It extends to an implemented class with necessary attributes.
 */
public interface ProfileManagerSA {

	// Profile Creation //

	/**
	 * Adds a new club to the ClubDAOImp database.
	 * @param id club's id (club's known name)
	 * @param location club's location (coordinates?)
	 * @param price ticket price [0 -> free entrance]
	 * @param tags list of characteristic tags used in filtering
	 */
	public void addNewClub(String id, String location, float price, List<String> tags);

	/**
	 * Adds a new user to the UserDAOImp database.
	 * @param id user's id (username)
	 * @param password user's login password
	 * @param email user's email account direction
	 * @param name user's name
	 * @param birthday user's birthday
	 */
	public void addNewUser(String id, String password, String email, String name, Date birthday);

	
	// Profile Modification //

	/**
	 * Given a club and a valid type of club data, overwrites the existing data;
	 * provided the given data is correct/suitable.
	 * @param id id of club to be modified
	 * @param dataType type of data to be modified
	 * @param newData new data
	 */
	public void modifyClubData(String id, String dataType, Object newData);
	
	/**
	 * Given a user and a valid type of user data, overwrites the existing data;
	 * provided the given data is correct/suitable.
	 * @param id id of user to be modified
	 * @param dataType type of data to be modified
	 * @param newData new data
	 */
	public void modifyUserData(String id, String dataType, Object newData);

	
	// Profile Removal //

	/**
	 * Removes the club from the ClubDAOImp database if found.
	 * @param id id from club to be removed
	 */
	public void removeClub(String id);

	/**
	 * Removes the user from the UserDAOImp database if found.
	 * @param id id from user to be removed
	 */
	public void removeUser(String id);
}