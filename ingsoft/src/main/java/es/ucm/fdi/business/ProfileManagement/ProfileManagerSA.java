package es.ucm.fdi.business.ProfileManagement;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.zip.DataFormatException;

import es.ucm.fdi.integration.data.Location;
import es.ucm.fdi.integration.util.ReviewPOJO;


/**
 * Interface defining the functionalities to be used in 
 * profile management.
 * It extends to an implemented class with necessary attributes.
 * 
 * @author behergue 	[Beatriz Herguedas]
 * @author PabloHdez 	[Pablo Hernández]
 */
public interface ProfileManagerSA {

	//** PROFILE CREATION **//

	/**
	 * Adds a new <code>Club</code> to the <code>ClubDAO</code> database.
	 * 
	 * @param clubID <code>String</code> with the new club <code>ID</code>
	 * @param name <code>String</code> with the new club <code>CommercialName</code>
	 * @param address <code>String</code> with the new club <code>Address</code>
	 * @param price <code>String</code> with the new club <code>TicketPrice</code>
	 * @param tags <code>Set<String></code> with list of the new club <code>Tags</code>
	 * 
	 * @throws IllegalArgumentException if <code>clubID</code> already stored in <code>clubDAO</code> database
	 */
	public void addNewClub(String clubID, String name, String address, float price, Set<String> tags)
			throws IllegalArgumentException, DataFormatException;

	/**
	 * Adds a new <code>User</code> to the <code>UserDAO</code> database.
	 * 
	 * @param userID <code>String</code> with the new user <code>ID</code>
	 * @param password <code>String</code> with the new user <code>Password</code>
	 * @param email <code>String</code> with the new user <code>Email</code>
	 * @param name <code>String</code> with the new user <code>Name</code>
	 * @param birthday <code>LocalDate</code> with the new club <code>Birthday</code>
	 * 
	 * @throws IllegalArgumentException if <code>userID</code> already stored in <code>userDAO</code> database
	 */
	public void addNewUser(String userID, String username, String password, String email, String name, LocalDate birthday) 
			throws IllegalArgumentException, DataFormatException;











	//** CLUB MODIFICATION **//

	/**
	 * Modifies a club <code>ID</code> if the data is correct.
	 * 
	 * @param newID <code>String</code> with new <code>ID</code>
	 * @param clubID <code>ID</code> of <code>Club</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>clubID</code> not found in <code>clubDAO</code> database
	 * @throws DataFormatException if <code>newID</code> parsing failed
	 */
	public void modifyClubID(String newID, String clubID) 
			throws NoSuchElementException, DataFormatException;

	/**
	 * Modifies a club <code>CommercialName</code> if the data is correct.
	 * 
	 * @param newCommercialName <code>String</code> with new <code>CommercialName</code>
	 * @param clubID <code>ID</code> of <code>Club</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>clubID</code> not found in <code>clubDAO</code> database
	 * @throws DataFormatException if <code>newCommercialName</code> parsing failed
	 */
	public void modifyClubCommercialName(String newCommercialName, String clubID) 
			throws NoSuchElementException, DataFormatException;

	/**
	 * Modifies club <code>Address</code> if the data is correct.
	 * 
	 * @param newAddress <code>String</code> with new <code>Address</code>
	 * @param clubID <code>ID</code> of <code>Club</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>clubID</code> not found in <code>clubDAO</code> database
	 * @throws DataFormatException if <code>newAddress</code> parsing failed
	 */
	public void modifyClubAddress(String newAddress, String clubID) 
			throws NoSuchElementException, DataFormatException ;

	/**
	 * Modifies a club <code>TicketPrice</code> if the data is correct.
	 * 
	 * @param newPrice <code>Float</code> with new <code>TicketPrice</code>
	 * @param clubID <code>ID</code> of <code>Club</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>clubID</code> not found in <code>clubDAO</code> database
	 * @throws DataFormatException if <code>newPrice</code> parsing failed
	 */
    public void modifyClubPrice(Float newPrice, String clubID) 
			throws NoSuchElementException, DataFormatException;

	/**
	 * Modifies a club <code>Location</code> if the data is correct.
	 * 
	 * @param newLocation <code>Location</code> with the new club placement
	 * @param clubID <code>ID</code> of <code>Club</code> to be modified 
	 * 
	 * @throws NoSuchElementException if <code>clubID</code> not found in <code>clubDAO</code> database
	 */
	public void modifyClublocation(Location newLocation, String clubID)
			throws NoSuchElementException;

	/**
	 * Modifies a club <code>Rating</code> if the data is correct.
	 * ATTENTION: This method is not thought to be used
	 * initially in the general implementation.
	 * 
	 * @param newRating <code>Float</code> with new <code>Rating</code>
	 * @param clubID <code>ID</code> of <code>Club</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>clubID</code> not found in <code>clubDAO</code> database
	 * @throws DataFormatException if <code>newRating</code> parsing failed
	 */
    public void modifyClubRating(Float newRating, String clubID) 
			throws NoSuchElementException, DataFormatException;

	/**
	 * Adds a new <code>Tag</code> to the club <code>Tags</code> if
	 * the new <code>Tag</code> is correct.
	 * 
	 * @param newTag <code>String</code> with new <code>Tag</code>
	 * @param clubID <code>ID</code> of <code>Club</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>clubID</code> not found in <code>clubDAO</code> database
	 * @throws DataFormatException if <code>newTag</code> parsing failed
	 */
    public void addClubTag(String newTag, String clubID) 
			throws NoSuchElementException, DataFormatException;

	/**
	* Removes a <code>Tag</code> from the club <code>Tags</code> if
	* the new <code>tag</code> is correct.
	* 
	* @param tagToRemove <code>String</code> with <code>Tag</code> to be removed
	* @param clubID <code>ID</code> of <code>Club</code> to be modified
	* 
	@throws NoSuchElementException if <code>clubID</code> not found in <code>clubDAO</code> database
	* @throws DataFormatException if <code>tagToRemove</code> parsing failed
	*/
    public void removeClubTag(String tagToRemove, String clubID) 
			throws NoSuchElementException, DataFormatException;

	/**
	 * Clears the club <code>Tags</code>.
	 * 
	 * @param clubID <code>ID</code> of <code>Club</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>clubID</code> not found in <code>clubDAO</code> database
	 */
	public void clearClubTags(String clubID)
			throws NoSuchElementException;











	//** USER MODIFICATION **//

	/**
	 * Modifies the user's id if the data is correct.
	 * 
	 * @param newData <code>String</code> with new <code>ID</code>
	 * @param userID <code>ID</code> of <code>User</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>userID</code> not found in <code>userDAO</code> database
	 * @throws DataFormatException if <code>newID</code> parsing failed
	 */
	public void modifyUserID(String newID, String userID)
			throws NoSuchElementException, DataFormatException;

	/**
	 * Modifies the user's username if the data is correct.
	 * 
	 * @param newUsername <code>String</code> with new <code>Username</code>
	 * @param userID <code>ID</code> of <code>User</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>userID</code> not found in <code>userDAO</code> database
	 * @throws DataFormatException if <code>newUsername</code> parsing failed
	 */
    public void modifyUserUsername(String newUsername, String userID) 
			throws NoSuchElementException, DataFormatException;


	/**
	 * Modifies the user's password if the data is correct.
	 * 
	 * @param newPassword <code>String</code> with new <code>Password</code>
	 * @param userID <code>ID</code> of <code>User</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>userID</code> not found in <code>userDAO</code> database
	 * @throws DataFormatException if <code>newPassword</code> parsing failed
	 */
    public void modifyUserPassWord(String newPassword, String userID) 
			throws NoSuchElementException, DataFormatException;

	/**
	 * Modifies the user's email if the data is correct.
	 * 
	 * @param newEmail <code>String</code> with new <code>Email</code>
	 * @param userID <code>ID</code> of <code>User</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>userID</code> not found in <code>userDAO</code> database
	 * @throws DataFormatException if <code>newEmail</code> parsing failed
	 */
    public void modifyUserEmail(String newEmail, String userID) 
			throws NoSuchElementException, DataFormatException;

	/**
	 * Modifies the user's name if the data is correct.
	 * 
	 * @param newName <code>String</code> with new <code>Name</code>
	 * @param userID <code>ID</code> of <code>User</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>userID</code> not found in <code>userDAO</code> database
	 * @throws DataFormatException if <code>newName</code> parsing failed
	 */
    public void modifyUserName(String newName, String userID) 
			throws NoSuchElementException, DataFormatException;

	/**
	 * Modifies the user's birthday if the data is correct.
	 * 
	 * @param newBirthday <code>LocalDate</code> with new <code>Birthday</code>
	 * @param userID <code>ID</code> of <code>User</code> to be modified
	 * 
	 * @throws NoSuchElementException if <code>userID</code> not found in <code>userDAO</code> database
	 * @throws DataFormatException if <code>newBirthday</code> parsing failed
	 */
    public void modifyUserBirthday(LocalDate newBirthday, String userID) 
			throws NoSuchElementException, DataFormatException;
	










	//** PROFILE REMOVAL **//

	/**
	 * Removes the <code>Club</code> from the <code>ClubDAO</code> database if found.
	 * 
	 * @param clubID <code>ID</code> of <code>Club</code> to be removed
	 * 
	 * @throws NoSuchElementException if <code>clubID</code> not found in <code>clubDAO</code> database
	 */
	public void removeClub(String clubID) throws NoSuchElementException;

	/**
	 * Removes the <code>User</code> from the <code>UserDAO</code> database if found.
	 * 
	 * @param userID <code>ID</code> of <code>User</code> to be removed
	 * 
	 * @throws NoSuchElementException if <code>userID</code> not found in <code>userDAO</code> database
	 */
	public void removeUser(String userID) throws NoSuchElementException;











	//** PROFILE UPDATING **//

	/**
	 * <p>
	 * Adds a new or modified user <code>Review</code> and recalculates 
	 * the club's total <code>Rating</code>.
	 * </p> <p>
	 * <code>Club</code> receives <code>Rate</code> from <code>User</code>.
	 * </p>
	 * 
	 * @param clubID <code>ID</code> of <code>Club</code> to be reviewed
	 * @param review <code>ReviewPOJO</code> with user <code>Review</code>
	 * @param userID <code>ID</code> of the reviewing <code>User</code>
	 * 
	 * @throws NoSuchElementException if <code>userID, clubID</code> not found in <code>userDAO, clubDAO</code> databases
	 * @throws DataFormatException if <code>rate</code> parsing failed
	 */
	public void addReview(String clubID, ReviewPOJO review, String userID) 
			throws NoSuchElementException, DataFormatException;

	/**
	 * <p>
	 * Removes a user <code>Review</code>.
	 * </p> <p>
	 * <code>Club</code> deletes <code>Review</code> from <code>User</code>.
	 * </p>
	 * 
	 * @param clubID <code>ID</code> of reviewed <code>Club</code> 
	 * @param userID <code>ID</code> of <code>User</code> removing the <code>Review</code>
	 * 
	 * @throws NoSuchElementException if <code>userID, clubID</code> not found in <code>userDAO, clubDAO</code> databases
	 */
	public void removeReview(String clubID, String userID) 
			throws NoSuchElementException;
}