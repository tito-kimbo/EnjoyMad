package es.ucm.fdi.business.profilemanagement;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.zip.DataFormatException;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.profilemanagement.tools.ClubModifierBO;
import es.ucm.fdi.business.profilemanagement.tools.UserModifierBO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.ReviewPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Interface defining the functionalities to be used in profile management. It
 * extends to an implemented class with necessary attributes.
 * 
 * @version 08.05.2018
 * 
 * @author behergue [Beatriz Herguedas]
 * @author PabloHdez [Pablo Hernández]
 */
public interface ProfileManagerSA {

	// ** PROFILE CREATION **//

	/**
     * <p>
     * * DO NOT USE IN MAIN APP * Method is kept just in case for testing or future
     * implementations.
     * </p>
     * <p>
     * Builds a new {@code ClubPOJO} from the given arguments and adds a new to the
     * app {@code ClubDAO} database.
     * </p>
     * 
     * @param clubID  - {@code String} with the new club {@code ID}
     * @param name    - {@code String} with the new club {@code CommercialName}
     * @param address - {@code String} with the new club {@code Address}
     * @param price   - {@code String} with the new club {@code TicketPrice}
     * @param tags    - {@code Set<String>} with list of the new club {@code Tags}
     * 
     * @throws IllegalArgumentException if {@code clubID} already stored in
     *                                  {@code ClubDAO} database
     * @throws DataFormatException      if parsing failed while checking the new
     *                                  club attributes
     */
	public void addNewClub(String clubID, String name, String address,
			float price, Set<TagPOJO> tags) throws IllegalArgumentException, 
            DataFormatException;
	

    /**
     * Adds a given {@code ClubPOJO} to the app {@code ClubDAO} database, 
     * rechecking its arguments for security (which have been supposedly 
     * checked externally).
     * 
     * @param club - new {@code ClubPOJO} to be added
     * 
     * @throws IllegalArgumentException 	if {@code club} id already stored
	 * 										in {@code ClubDAO} database
     * @throws DataFormatException          if parsing failed while checking the
     *                                      new club attributes
     * 
     */
    public void addNewClub(ClubPOJO club) throws IllegalArgumentException, 
            DataFormatException;






	/**
     * <p>
     * * DO NOT USE IN MAIN APP * Method is kept just in case for testing or 
     * future implementations.
     * </p> 
     * <p>
     * Builds a new {@code UserPOJO} from the given arguments and adds a new to
     * the app {@code UserDAO} database.
     * </p>
     * 
     * @param userID   - {@code String} with the new user {@code ID}
     * @param username - {@code String} with the new user {@code Username}
     * @param password - {@code String} with the new user {@code Password}
     * @param email    - {@code String} with the new user {@code Email}
     * @param name     - {@code String} with the new user {@code TicketPrice}
     * @param birthday - {@code Set<String>} with list of the new user 
     *                   {@code Tags}
     * 
     * @throws IllegalArgumentException if {@code userID} already stored in
     *                                  {@code UserDAO} database
     * @throws DataFormatException      if parsing failed while checking the 
     *                                  new user attributes
     */
	public void addNewUser(String userID, String username, String password, 
			String email, String name, LocalDate birthday) 
			throws IllegalArgumentException, DataFormatException;

    /**
     * <p>
     * Adds a given {@code UserPOJO} to the app {@code UserDAO} database, 
     * rechecking its arguments for security (which have been supposedly 
     * checked externally).
     * </p>
     * <p>
     * * PASSWORD SECURITY * The user passed as argument has already had
     * the introduced password hashed.
     * </p>
     * 
     * @param user - new {@code UserPOJO} to be added
     *
     * @throws IllegalArgumentException if {@code user} id already stored in
     *                                  {@code userDAO} database
     * @throws DataFormatException      if parsing failed while checking the 
     *                                  new user attributes
     */
    public void addNewUser(UserPOJO user) 
			throws IllegalArgumentException, DataFormatException;

    
	
	
	
	
	// ** CLUB MODIFICATION **//

    /**
     * Modifies a specific atribute of the indicated {@code ClubPOJO}, provided
     * it exists and the modification is valid.
     * 
     * @param clubID   - id of {@code ClubPOJO} to be modified
     * @param dataType - {@code ClubModifierBO} indicating the specific
     *                   modification
     * @param newData  - new data to replace the old one in the modification
     * 
     * @throws NoSuchElementException   if {@code clubID} not found in
     *                                  {@code clubDAO} database
     * @throws IllegalArgumentException if {@code newData} class does not match
     *                                  that needed for the modification
     * @throws DataFormatException      if {@code newData} parsing failed
     */
    public void modifyClubData(String clubID, ClubModifierBO dataType, 
            Object newData) throws NoSuchElementException, 
            IllegalArgumentException, DataFormatException;

	// ** USER MODIFICATION **//

    /**
     * Modifies a specific atribute of the indicated {@code UserPOJO}, provided
     * it exists and the modification is valid.
     * 
     * @param userID   - id of {@code UserPOJO} to be modified
     * @param dataType - {@code UserModifierBO} indicating the specific
     *                   modification
     * @param newData  - new data to replace the old one in the modification
     * 
     * @throws NoSuchElementException   if {@code userID} not found in
     *                                  {@code userDAO} database
     * @throws IllegalArgumentException if {@code newData} class does not match
     *                                  that needed for the modification
     * @throws DataFormatException      if {@code newData} parsing failed
     */
	public void modifyUserData(String userID, UserModifierBO dataType, 
            Object newData) throws IllegalArgumentException,
            DataFormatException;

    
	
	
	
	
	
	// ** PROFILE REMOVAL **//

    /**
     * Removes the {@code ClubPOJO} from the {@code ClubDAO} database if found.
     * 
     * @param clubID - id of {@code ClubPOJO} to be removed
     * 
     * @throws NoSuchElementException if {@code clubID} not found in
     *                                {@code clubDAO} database
     */
    public void removeClub(String clubID) throws NoSuchElementException;

    /**
     * Removes the {@code UserPOJO} from the {@code UserDAO} database if found.
     * 
     * @param userID - id of {@code UserPOJO} to be removed
     * 
     * @throws NoSuchElementException if {@code userID} not found in
     *                                {@code userDAO} database
     */
    public void removeUser(String userID) throws NoSuchElementException;

    
	
	
	
	
	// ** REVIEW UPDATING **//

    /**
     * <p>
     * Adds a new or modified user {@code Review} and recalculates the club
     * total {@code Rating}.
     * </p>
     * <p>
     * {@code ClubPOJO} receives {@code Rate} from {@code UserPOJO}.
     * </p>
     * 
     * @param clubID - id of {@code ClubPOJO} to be reviewed
     * @param review - {@code ReviewPOJO} with user {@code Review}
     * @param userID - id of the reviewing {@code UserPOJO}
     * 
     * @throws NoSuchElementException if {@code userID, clubID} not found in
     *                                {@code userDAO, clubDAO} databases
     * @throws DataFormatException    if {@code review} parsing failed
     */
    public void addReview(String clubID, ReviewPOJO review, String userID) 
            throws NoSuchElementException, DataFormatException;

    /**
     * Removes a user {@code ReviewPOJO}. 
     * 
     * @param clubID - id of reviewed {@code ClubPOJO}
     * @param userID - id of {@code UserPOJO} removing the 
     *                 {@code ReviewPOJO}
     * 
     * @throws NoSuchElementException if {@code userID} or {@code clubID} not 
     *                                found in their respective DAOs
     */
    public void removeReview(String clubID, String userID) throws 
            NoSuchElementException;
}