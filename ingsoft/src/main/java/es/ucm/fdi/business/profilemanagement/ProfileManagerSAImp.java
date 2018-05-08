package es.ucm.fdi.business.profilemanagement;

import java.time.LocalDate;
import java.util.Set;

import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

import org.mindrot.jbcrypt.BCrypt;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.profilemanagement.tools.ClubModifierBO;
import es.ucm.fdi.business.profilemanagement.tools.UserModifierBO;
import es.ucm.fdi.business.util.ParsingToolHelper;

import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ReviewPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Class to be used as the Profile Manager of the application. 
 * It implements the functionality of {@link ProfileManagerSA}.
 * 
 * @version 08.05.2018
 */
public class ProfileManagerSAImp implements ProfileManagerSA {

    private static ClubDAOImp clubDAO;
    private static UserDAOImp userDAO;

    /**
     * <p>
     * Builds a {@link ProfileManagerSAImp ProfileManager} whose funcionality 
     * is:
     * </p> <p>
     * 1)   To add and remove {@code UserPOJO}s and {@code ClubPOJO}s.
     * </p> <p>
     * 2)   To modify {@code UserPOJO}s and {@code ClubPOJO}s inner attributes.
     * </p> <p>
     * 3)   To handle users feedback of clubs with {@code ReviewPOJO}s.
     * </p>
     * 
     * @param clubs  - the app {@code ClubDAO} database
     * @param users  - the app {@code UserDAO} database 
     */
    public ProfileManagerSAImp(ClubDAOImp clubs, UserDAOImp users) {
        clubDAO = clubs;
        userDAO = users;
    }






  
     * {@inheritDoc}
     * 
     * @param clubID  {@inheritDoc}
     * @param name    {@inheritDoc}
     * @param address {@inheritDoc}
     * @param price   {@inheritDoc}
     * @param tags    {@inheritDoc}
     */
    public void addNewClub(String clubID, String name, String address, 
            float price, Set<TagPOJO> tags) throws IllegalArgumentException, 
            DataFormatException {
        
        // Is already registered?
        if ( clubDAO.exist(clubID) ) {

            throw new IllegalArgumentException(
                "In CLUB creation:" + 
                "clubID is already registered -> " + 
                clubID
            );
        }

        // Arguments are checked
        if ( ! ParsingToolHelper.parseID(clubID) ) {
            throw new DataFormatException(
                "In CLUB creation: " + 
                "not a valid ID format -> " + 
                clubID
            );
        }

        if ( ! ParsingToolHelper.parseCommercialName(name) ) {
            throw new DataFormatException(
                "In CLUB creation: " + 
                "not a valid commercial name format -> " + 
                name
            );
        }
                
        if ( ! ParsingToolHelper.parseAddress(address) ) {
            throw new DataFormatException(
                "In CLUB creation: " + 
                "not a valid address format -> " + 
                address
            );
        }

        if ( ! ParsingToolHelper.parsePrice(price) ) {
            throw new DataFormatException(
                "In CLUB creation: " + 
                "not a valid price -> " + 
                price
            );
        }
        
        /*
        if ( ! ParsingToolHelper.parseTags(tags) ) {

            String tagsInfo = "";
            for (String t : tags) {
                tagsInfo += t + " ";
            }

            throw new DataFormatException(
                "In CLUB creation: " + 
                "not a set of tags -> " + 
                tagsInfo
            );
        }
        */

        // Club creation and addition to database.
        ClubPOJO newClub = new ClubPOJO(clubID, name, address, price, tags);
        clubDAO.addClub(newClub);
    }

    /**
     * {@inheritDoc}
     * 
     * @param club {@inheritDoc}
     */
    public void addNewClub(ClubPOJO club) throws IllegalArgumentException, 
            DataFormatException {
                
        // Is already registered?
        if ( clubDAO.exist( club.getID() ) ) {
            throw new IllegalArgumentException(
                "In CLUB creation:" + 
                "clubID is already registered -> " + 
                club.getID()
            );
        }

        // Arguments are checked
        if ( ! ParsingToolHelper.parseID( club.getID() ) ) {
            throw new DataFormatException(
                "In CLUB creation: " + 
                "not a valid ID format -> " + 
                club.getID()
            );
        }

        if ( ! ParsingToolHelper.parseCommercialName( club.getCommercialName() ) ) {
            throw new DataFormatException(
                "In CLUB creation: " + 
                "not a valid commercial name format -> " + 
                club.getCommercialName()
            );
        }

        if ( ! ParsingToolHelper.parseAddress( club.getAddress() ) ) {
            throw new DataFormatException(
                "In CLUB creation: " + 
                "not a valid address format -> " + 
                club.getAddress()
            );
        }

        if ( ! ParsingToolHelper.parsePrice( club.getPrice() ) ) {
            throw new DataFormatException(
                "In CLUB creation: " + 
                "not a valid price -> " + 
                club.getPrice()
            );
        }

        // Addition to database.
        clubDAO.addClub(club);
    }


    
    
    

    /**
     * {@inheritDoc}
     * 
     * @param userID   {@inheritDoc}
     * @param username {@inheritDoc}
     * @param password {@inheritDoc}
     * @param email    {@inheritDoc}
     * @param name     {@inheritDoc}
     * @param birthday {@inheritDoc}
     */
    public void addNewUser(String userID, String username, String password, 
            String email, String name, LocalDate birthday) 
            throws IllegalArgumentException, DataFormatException {
        
        // Is already registered?
        if ( userDAO.exist(userID) ) {
            throw new IllegalArgumentException(
                "In USER creation: " +
                "userID is already registered -> " +
                userID
            );
        }

        // Arguments are checked
        if ( ! ParsingToolBO.parseID(userID) ) {
            throw new DataFormatException(
                "In USER creation: " + 
                "not a valid ID format -> " + 
                userID
            );
        }

        if ( ! ParsingToolBO.parseUsername(username) ) {
            throw new DataFormatException(
                "In USER creation: " + 
                "not a valid username format -> " 
                + username
            );
        }

        if ( ! ParsingToolBO.parsePassword(password) ) {
            throw new DataFormatException(
                "In USER creation: " +
                "not a valid password format -> " + 
                password
            );
        }

        if ( ! ParsingToolBO.parseEmail(email) ) {
            throw new DataFormatException(
                "In USER creation: " + 
                "not a valid email format -> " + 
                email
            );
        }

        if ( ! ParsingToolBO.parseName(name) ) {
            throw new DataFormatException(
                "In USER creation: " + 
                "not a valid name format -> " + 
                name
            );
        }

        if ( ! ParsingToolBO.parseBirthday(birthday) ) {
            throw new DataFormatException(
                "In USER creation: " + 
                "not a valid birth date -> " + 
                birthday
            );
        }

        // Password protection
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // User creation and addition
        UserPOJO newUser = new UserPOJO(userID, username, hashPassword, email, name, birthday);
        userDAO.addUser(newUser);
    }

    /**
     * {@inheritDoc}
     * 
     * @param user {@inheritDoc}
     */
    public void addNewUser(UserPOJO user) throws IllegalArgumentException,
        DataFormatException {
        
        // Is already registered?
        if ( userDAO.exist( user.getID() ) ) {
            throw new IllegalArgumentException(
                "In USER creation: " +
                "userID is already registered -> " +
                user.getID()
            );
        }

        // Arguments are checked
        if ( ! ParsingToolBO.parseID( user.getID() ) ) {
            throw new DataFormatException(
                "In USER creation: " + 
                "not a valid ID format -> " + 
                user.getID()
            );
        }

        if ( ! ParsingToolBO.parseUsername( user.getUsername() ) ) {
            throw new DataFormatException(
                "In USER creation: " + 
                "not a valid username format -> " +
                user.getUsername()
            );
        }

        if ( ! ParsingToolBO.parseEmail( user.getEmail() ) ) {
            throw new DataFormatException(
                "In USER creation: " + 
                "not a valid email format -> " + 
                user.getEmail()
            );
        }

        if ( ! ParsingToolBO.parseName( user.getName() ) ) {
            throw new DataFormatException(
                "In USER creation: " + 
                "not a valid name format -> " + 
                user.getName()
            );
        }

        if ( ! ParsingToolBO.parseBirthday( user.getBirthday() ) ) {
            throw new DataFormatException(
                "In USER creation: " + 
                "not a valid birth date -> " + 
                user.getBirthday()
            );
        }

        // Addition to database.
        userDAO.addUser(user);
    }








    /**
     * {@inheritDoc}
     * 
     * @param clubID   {@inheritDoc}
     * @param dataType {@inheritDoc}
     * @param newData  {@inheritDoc}
     */
    public void modifyClubData(String clubID, ClubModifierBO dataType,
            Object newData) throws NoSuchElementException, 
            IllegalArgumentException, DataFormatException {

        ClubPOJO club = clubDAO.getClub(clubID);

        if (club == null) {
            throw new NoSuchElementException(
                "In CLUB modification: " + 
                "club not found in database. ID -> " + 
                clubID
            );
        }

        // Valid newData?
        try {
            dataType.modify(club, newData);
        } catch (IllegalArgumentException ilegArg) {
            throw ilegArg;
        } catch (DataFormatException dataForm) {
            throw dataForm;
        }        
    }

    /**
     * {@inheritDoc}
     * 
     * @param userID   {@inheritDoc}
     * @param dataType {@inheritDoc}
     * @param newData  {@inheritDoc}
     */
    public void modifyUserData(String userID, UserModifierBO dataType,
            Object newData) throws IllegalArgumentException,
            DataFormatException {

        UserPOJO user = userDAO.getUser(userID);

        if (user == null) {
            throw  new NoSuchElementException(
                "In USER modification: " + 
                "user not found in database. ID -> " + 
                userID
            );
        }

        // Valid newData?
        try {
            dataType.modify(user, newData);
        } catch (IllegalArgumentException ilegArg) {
            throw ilegArg;
        } catch (DataFormatException dataForm) {
            throw dataForm;
        }
    }



    /**
     * {@inheritDoc}
     * 
     * @param clubID {@inheritDoc}
     */
    public void removeClub(String clubID) throws NoSuchElementException {
        ClubPOJO removingClub = clubDAO.getClub(clubID);

        if (removingClub == null) {
            throw new NoSuchElementException(
                "In CLUB removal: " +
                "club not found in database. ID -> " + 
                clubID
            );
        }

        // Removal of reviewers (in user attributes)
        for (String userID : removingClub.getReviewers()) {
            UserPOJO rater = userDAO.getUser(userID);

            rater.removeFromReviewed(clubID);
        }

        clubDAO.removeClub(clubID);
    }

    /**
     * {@inheritDoc}
     * 
     * @param userID {@inheritDoc}
     */
    public void removeUser(String userID) throws NoSuchElementException {
        UserPOJO removingUser = userDAO.getUser(userID);

        if (removingUser == null) {
            throw new NoSuchElementException(
                "In USER removal: " + 
                "user not found in database. ID -> " + 
                userID
            );
        }

        // Removal of user reviews
        for (String clubID : removingUser.getReviewedClubs()) {
            ClubPOJO reviewedClub = clubDAO.getClub(clubID);

            reviewedClub.removeUserReview(userID);
        }

        userDAO.removeUser(userID);
    }







    /**
     * {@inheritDoc}
     * 
     * @param clubID {@inheritDoc}
     * @param review {@inheritDoc}
     * @param userID {@inheritDoc}
     */
    public void addReview(String clubID, ReviewPOJO review, String userID)
            throws NoSuchElementException, DataFormatException {
        ClubPOJO reviewedClub = clubDAO.getClub(clubID);
        UserPOJO reviewingUser = userDAO.getUser(userID);

        if (reviewedClub == null) {
            throw new NoSuchElementException("In REVIEW adding: reviewed club not found in database. ID -> " + clubID);
        }

        if (reviewingUser == null) {
            throw new NoSuchElementException("In REVIEW adding: reviewing user not found in database. ID -> " + userID);
        }

        // Valid?
        if (!ParsingToolBO.parseReview(review)) {
            throw new DataFormatException("In REVIEW adding: not a valid user review -> " + review);
        }

        reviewedClub.addUserReview(userID, review);
        reviewingUser.addToReviewed(clubID);
    }

    /**
     * {@inheritDoc}
     * 
     * @param clubID {@inheritDoc}
     * @param userID {@inheritDoc}
     */
    public void removeReview(String clubID, String userID) throws NoSuchElementException {
        ClubPOJO unreviewedClub = clubDAO.getClub(clubID);
        UserPOJO unreviewingUser = userDAO.getUser(userID);

        if (unreviewedClub == null) {
            throw new NoSuchElementException(
                    "In OPINION removal: unreviewed club not found in database. ID -> " + clubID);
        }

        if (unreviewingUser == null) {
            throw new NoSuchElementException(
                    "In OPINION removal: unreviewing user not found in database. ID -> " + userID);
        }

        unreviewedClub.removeUserReview(userID);
    }
}