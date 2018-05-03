package es.ucm.fdi.business.profilemanagement;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.zip.DataFormatException;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.profilemanagement.tools.ClubDataType;
import es.ucm.fdi.business.profilemanagement.tools.UserDataType;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.ReviewPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Interface defining the functionalities to be used in profile management. It
 * extends to an implemented class with necessary attributes.
 * 
 * @author behergue [Beatriz Herguedas]
 * @author PabloHdez [Pablo Hernández]
 */
public interface ProfileManagerSA {

    
	
	
	
	
	// ** PROFILE CREATION **//

	/**
	 * Builds a new {@code ClubPOJO} from the given arguments 
	 * and adds a new to the app {@code ClubDAO} database.
	 * 
	 * @param clubID 		- {@code String} with the new 
	 * 						club {@code ID}
	 * @param name 			- {@code String} with the new 
	 * 						club {@code CommercialName}
	 * @param address 		- {@code String} with the new 
	 * 						club {@code Address}
	 * @param price 		- {@code String} with the new 
	 * 						club {@code TicketPrice}
	 * @param tags 			- {@code Set<String>} with list 
	 * 						of the new club {@code Tags}
	 * 
	 * @throws IllegalArgumentException 	if {@code clubID} already stored 
	 * 										in {@code ClubDAO} database
	 * @throws DataFormatException			if parsing failed while checking
	 * 										the new club attributes
	 */
	public void addNewClub(String clubID, String name, String address,
			float price, Set<TagPOJO> tags)
			throws IllegalArgumentException, DataFormatException;
	

    /**
     * Adds a given {@code ClubPOJO} to the app {@code ClubDAO}
	 * database, asumming its attributes have been checked 
	 * externally.
     * 
     * @param club 		- new {@code ClubPOJO} to be added
     * 
     * @throws IllegalArgumentException 	if {@code club} id already stored 
	 * 										in {@code ClubDAO} database
     */
    public void addNewClub(ClubPOJO club) 
			throws IllegalArgumentException, DataFormatException;






	/**
	 * Builds a new {@code UserPOJO} from the given arguments 
	 * and adds a new to the app {@code UserDAO} database.
	 * 
	 * @param userID 		- {@code String} with the new 
	 * 						user {@code ID}
	 * @param username		- {@code String} with the new
	 * 						user {@code Username}
	 * @param password 		- {@code String} with the new 
	 * 						user {@code Password}
	 * @param email 		- {@code String} with the new 
	 * 						user {@code Email}
	 * @param name 			- {@code String} with the new 
	 * 						user {@code TicketPrice}
	 * @param birthday 		- {@code Set<String>} with list 
	 * 						of the new user {@code Tags}
	 * 
	 * @throws IllegalArgumentException 	if {@code userID} already stored 
	 * 										in {@code UserDAO} database
	 * @throws DataFormatException			if parsing failed while checking
	 * 										the new user attributes
	 */
	public void addNewUser(String userID, String username, String password, 
			String email, String name, LocalDate birthday) 
			throws IllegalArgumentException, DataFormatException;

    /**
     * Adds a given {@code UserPOJO} to the app {@code UserDAO}
	 * database, asumming its attributes have been checked 
	 * externally.
     * 
     * @param club 		- new {@code UserPOJO} to be added
     * 
     * @throws IllegalArgumentException 	if {@code user} id already stored 
	 * 										in {@code userDAO} database
     */
    public void addNewUser(UserPOJO user) 
			throws IllegalArgumentException, DataFormatException;

    
	
	
	
	
	// ** CLUB MODIFICATION **//

    public void modifyClubData(String clubID, ClubDataType dataID, Object newData)
            throws DataFormatException;

	// ** USER MODIFICATION **//

	public void modifyUserData(String clubID, UserDataType dataID, Object newData)
            throws DataFormatException;

    
	
	
	
	
	
	// ** PROFILE REMOVAL **//

    /**
     * Removes the <code>Club</code> from the <code>ClubDAO</code> database if
     * found.
     * 
     * @param clubID <code>ID</code> of <code>Club</code> to be removed
     * 
     * @throws NoSuchElementException if <code>clubID</code> not found in
     *                                <code>clubDAO</code> database
     */
    public void removeClub(String clubID) throws NoSuchElementException;

    /**
     * Removes the <code>User</code> from the <code>UserDAO</code> database if
     * found.
     * 
     * @param userID <code>ID</code> of <code>User</code> to be removed
     * 
     * @throws NoSuchElementException if <code>userID</code> not found in
     *                                <code>userDAO</code> database
     */
    public void removeUser(String userID) throws NoSuchElementException;

    
	
	
	
	
	// ** REVIEW UPDATING **//

    /**
     * <p>
     * Adds a new or modified user <code>Review</code> and recalculates the club's
     * total <code>Rating</code>.
     * </p>
     * <p>
     * <code>Club</code> receives <code>Rate</code> from <code>User</code>.
     * </p>
     * 
     * @param clubID <code>ID</code> of <code>Club</code> to be reviewed
     * @param review <code>ReviewPOJO</code> with user <code>Review</code>
     * @param userID <code>ID</code> of the reviewing <code>User</code>
     * 
     * @throws NoSuchElementException if <code>userID, clubID</code> not found in
     *                                <code>userDAO, clubDAO</code> databases
     * @throws DataFormatException    if <code>rate</code> parsing failed
     */
    public void addReview(String clubID, ReviewPOJO review, String userID)
            throws NoSuchElementException, DataFormatException;

    /**
     * <p>
     * Removes a user <code>Review</code>.
     * </p>
     * <p>
     * <code>Club</code> deletes <code>Review</code> from <code>User</code>.
     * </p>
     * 
     * @param clubID <code>ID</code> of reviewed <code>Club</code>
     * @param userID <code>ID</code> of <code>User</code> removing the
     *               <code>Review</code>
     * 
     * @throws NoSuchElementException if <code>userID, clubID</code> not found in
     *                                <code>userDAO, clubDAO</code> databases
     */
    public void removeReview(String clubID, String userID) throws NoSuchElementException;
}