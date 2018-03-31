package es.ucm.fdi.business.ProfileManagement;

import java.util.Set;
import java.time.LocalDate;

import es.ucm.fdi.business.ProfileManagement.ManagementTools.ClubDataID;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.UserDataID;

/**
 * Interface defining the functionalities to be used in 
 * profile management.
 * It extends to an implemented class with necessary attributes.
 * @author behergue 	[Beatriz Herguedas]
 * @author PabloHdez 	[Pablo Hernández]
 */
public interface ProfileManagerSA {

	//** PROFILE CREATION **//

	/**
	 * Adds a new club to the ClubDAOImp database.
	 * @param id club's id (club's known name)
	 * @param location club's location (coordinates?)
	 * @param price ticket price [0 -> free entrance]
	 * @param tags list of characteristic tags used in filtering
	 */
	public void addNewClub(String id, String location, float price, Set<String> tags);

	/**
	 * Adds a new user to the UserDAOImp database.
	 * @param id user's id (username)
	 * @param password user's login password
	 * @param email user's email account direction
	 * @param name user's name
	 * @param birthday user's birthday
	 */
	public void addNewUser(String id, String password, String email, String name, LocalDate birthday);

	
	//** PROFILE MODIFICATION **//

	/**
	 * Given a club and a valid type of club data, overwrites the existing data;
	 * provided the given data is correct/suitable.
	 * 
	 * @param id id of club to be modified
	 * @param dataType type of data to be modified
	 * @param newData new data
	 */
	public void modifyClubData(String id, ClubDataID dataID, Object newData);
	
	/**
	 * Given a user and a valid type of user data, overwrites the existing data;
	 * provided the given data is correct/suitable.
	 * 
	 * @param id id of user to be modified
	 * @param dataType type of data to be modified
	 * @param newData new data
	 */
	public void modifyUserData(String id, UserDataID dataID, Object newData);

	
	//** PROFILE REMOVAL **//

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

	//** PROFILE UPDATING **//

	/**
	 * Adds a new or modified user rating and recalculates 
	 * the club's total rating.
	 * CLUB receives RATE from USER.
	 * @param clubID id of rated club
	 * @param rate rate
	 * @param userID rating user
	 */
	public void addNewRate(String clubID, int rate, String userID);

	/**
	 * Adds a new user opinion.
	 * CLUB receives OPINION from USER.
	 * @param clubID id of club
	 * @param opinion user's opinion
	 * @param userID id of user
	 */
	public void addNewOpinion(String clubID, String opinion, String userID);

	/**
	 * Removes a user rating.
	 * CLUB deletes rate from USER.
	 * @param clubID id of club
	 * @param userID id of user
	 */
	public void removeUserRate(String clubID, String userID);

	/**
	 * Removes a user opinion.
	 * CLUB deletes opinion from USER.
	 * @param clubID id of club
	 * @param userID id of user
	 */
	public void removeUserOpinion(String clubID, String userID);
}