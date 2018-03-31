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
	 * Adds a new <code>Club</code> to the <code>ClubDAO</code> database.
	 * 
	 * @param clubID club's id
	 * @param name club's commercial name
	 * @param address club's address
	 * @param price ticket price [0 -> free entrance]
	 * @param tags list of characteristic tags used in filtering
	 */
	public void addNewClub(String clubID, String name, String address, float price, Set<String> tags);

	/**
	 * Adds a new <code>User</code> to the <code>UserDAO</code> database.
	 * 
	 * @param userID user's id (username)
	 * @param password user's login password
	 * @param email user's email account direction
	 * @param name user's name
	 * @param birthday user's birthday
	 */
	public void addNewUser(String userID, String username, String password, String email, String name, LocalDate birthday);

	
	//** PROFILE MODIFICATION **//

	/**
	 * Given a <code>Club</code> and a valid type of club data, overwrites the existing data;
	 * provided the given data is correct/suitable.
	 * 
	 * @param clubID id of club to be modified
	 * @param dataType type of data to be modified
	 * @param newData new data
	 */
	public void modifyClubData(String clubID, ClubDataID dataID, Object newData);
	
	/**
	 * Given a <code>User</code> and a valid type of user data, overwrites the existing data;
	 * provided the given data is correct/suitable.
	 * 
	 * @param userID id of user to be modified
	 * @param dataType type of data to be modified
	 * @param newData new data
	 */
	public void modifyUserData(String userID, UserDataID dataID, Object newData);

	
	//** PROFILE REMOVAL **//

	/**
	 * Removes the <code>club</code> from the <code>ClubDAO</code> database if found.
	 * 
	 * @param clubID id from club to be removed
	 */
	public void removeClub(String clubID);

	/**
	 * Removes the <code>User</code> from the <code>UserDAO</code> database if found.
	 * 
	 * @param userID id from user to be removed
	 */
	public void removeUser(String userID);

	//** PROFILE UPDATING **//

	/**
	 * Adds a new or modified user rating and recalculates 
	 * the club's total rating.
	 * <code>Club</code> receives RATE from <code>User</code>.
	 * 
	 * @param clubID id of rated club
	 * @param rate rate
	 * @param userID rating user
	 */
	public void addNewRate(String clubID, int rate, String userID);

	/**
	 * Adds a new user opinion.
	 * <code>Club</code> receives OPINION from <code>User</code>.
	 * @param clubID id of club
	 * @param opinion user's opinion
	 * @param userID id of user
	 */
	public void addNewOpinion(String clubID, String opinion, String userID);

	/**
	 * Removes a user rating.
	 * <code>Club</code> deletes rate from <code>User</code>.
	 * @param clubID id of club
	 * @param userID id of user
	 */
	public void removeUserRate(String clubID, String userID);

	/**
	 * Removes a user opinion.
	 * <code>Club</code> deletes opinion from <code>User</code>.
	 * @param clubID id of club
	 * @param userID id of user
	 */
	public void removeUserOpinion(String clubID, String userID);
}