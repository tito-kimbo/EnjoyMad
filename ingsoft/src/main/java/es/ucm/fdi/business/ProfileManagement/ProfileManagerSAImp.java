package es.ucm.fdi.business.ProfileManagement;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.zip.DataFormatException;

import org.mindrot.jbcrypt.BCrypt;

import android.provider.ContactsContract.Profile;
import es.ucm.fdi.business.ProfileManagement.ManagementExceptions.AlreadyExistingProfileException;
import es.ucm.fdi.business.ProfileManagement.ManagementExceptions.ProfileManagementException;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.ClubDataID;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.ClubManageTool;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.ParsingTool;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.UserDataID;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.UserManageTool;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;


/**
 * Class to be used as the Profile Manager of the application. Its methods can only throw
 * <code>ProfileManagerException</code> exceptions which contain the cause of the exception.
 */
public class ProfileManagerSAImp implements ProfileManagerSA {
    private static ClubDAOImp clubDAO = new ClubDAOImp();
    private static UserDAOImp userDAO = new UserDAOImp();

    public ProfileManagerSAImp(ClubDAOImp clubs, UserDAOImp users) {
        clubDAO = clubs;
        userDAO = users;
    }

    public void addNewClub(String clubID, String name, String address, float price, Set<String> tags) 
            throws ProfileManagementException {
        // Is already registered?
        if (clubDAO.exist(clubID)) {
            throw new ProfileManagementException(
                new AlreadyExistingProfileException(
                    "In CLUB creation: clubID is already registered -> " + clubID
                )
            );
        }

        // Valid arguments? If not -> Exception throwing
        if ( ! ParsingTool.parseID(clubID) ) {
            throw new ProfileManagementException(
                new DataFormatException(
                    "In CLUB creation: not a valid ID format -> " + clubID
                )
            );
        }
        if ( ! ParsingTool.parseCommercialName(name) ) {
            throw new ProfileManagementException(
                new DataFormatException(
                    "In CLUB creation: not a valid commercial name format -> " + name
                )
            );
        }
        if ( ! ParsingTool.parseAddress(address) ) {
            throw new ProfileManagementException(
                new DataFormatException(
                    "In CLUB creation: not a valid address format -> " + address
                )
            );
        }
        if ( ! ParsingTool.parsePrice(price) ) {
            throw new ProfileManagementException(
                new DataFormatException(
                    "In CLUB creation: not a valid price -> " + price
                )
            );
        }
        if ( ! ParsingTool.parseTags(tags) ) {
            String tagsInfo = "";
            for (String t : tags) {
                tagsInfo += t + " ";
            }
            
            throw new ProfileManagementException(
                new DataFormatException(
                    "In CLUB creation: not a valid set of tags -> " + tagsInfo
                )
            );
        }

        ClubPOJO newClub = new ClubPOJO(clubID, name, address, price, tags);
        clubDAO.addClub(newClub);
    }

    public void addNewUser(String userID, String username, String password, String email, String name, LocalDate birthday) 
            throws ProfileManagementException {
        // Is already registered?
        if (userDAO.exist(userID)) {
            throw new ProfileManagementException(
                new AlreadyExistingProfileException(
                    "In USER creation: userID is already registered -> " + userID
                )
            );
        }

        // Valid arguments? If not -> Exception throwing
        if ( ! ParsingTool.parseID(userID) ) {
            throw new ProfileManagementException(
                new DataFormatException(
                    "In USER creation: not a valid ID format -> " + userID
                )
            );
        }
        if ( ! ParsingTool.parseUsername(username) ) {
            throw new ProfileManagementException(
                new DataFormatException(
                    "In USER creation: not a valid username format -> " + username
                )
            );
        }
        if ( ! ParsingTool.parsePassword(password) ) {
            throw new ProfileManagementException( 
                new DataFormatException(
                    "In USER creation: not a valid password format -> " + password
                )
            );
        }
        if ( ! ParsingTool.parseEmail(email) ) {
            throw new ProfileManagementException(
                new DataFormatException(
                   "In USER creation: not a valid email format -> " + email
                )
            );
        }
        if ( ! ParsingTool.parseName(name) ) {
            throw new ProfileManagementException(
                new DataFormatException(
                 "In USER creation: not a valid name format -> " + name
                )
            );
        }
        if ( ! ParsingTool.parseBirthday(birthday) ) {
            throw new ProfileManagementException(
                new DataFormatException(
                    "In USER creation: not a valid birth date -> " + birthday.toString()
                )
            );
        }

        // Password protection.
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        UserPOJO newUser = new UserPOJO(userID, username, hashPassword, email, name, birthday);
        userDAO.addUser(newUser);
    }

    public void modifyClubData(String clubID, ClubDataID dataID, Object newData)
            throws ProfileManagementException {
        ClubPOJO club = clubDAO.getClub(clubID);

        if (club == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In CLUB modification: club not found in database. ID -> " + clubID
                )
            );
        }

        // Valid newData?
        try {
            ClubManageTool clubManager = new ClubManageTool(club, clubDAO);
            clubManager.modify(dataID, newData);
        } catch (IllegalArgumentException ilegArg) {
            throw new ProfileManagementException(ilegArg);
        } catch (DataFormatException dataForm) {
            throw new ProfileManagementException(dataForm);
        }
    }
    
    public void modifyUserData(String userID, UserDataID dataID, Object newData)
            throws ProfileManagementException {
        UserPOJO user = userDAO.getUser(userID);

        if (user == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In USER modification: user not found in database. ID -> " + userID
                )
            );
        }

        // Valid newData?
        try {
            UserManageTool userManager = new UserManageTool(user, userDAO);
            userManager.modify(dataID, newData);
        } catch (IllegalArgumentException ilegArg) {
            throw new ProfileManagementException(ilegArg);
        } catch (DataFormatException dataForm) {
            throw new ProfileManagementException(dataForm);
        }        
    }

    public void removeClub(String clubID) throws ProfileManagementException {
        ClubPOJO removingClub = clubDAO.getClub(clubID);

        if (removingClub == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In CLUB removal: club not found in database. ID -> " + clubID
                )
            );
        }

        // Removal of raters (in user attributes)
        for ( String userID : removingClub.getRaters() ) {
            UserPOJO rater = userDAO.getUser(userID);

            rater.removeRated(clubID);
        }

        // Removal of reviewers (in user attributes)
        for ( String userID : removingClub.getReviewers() ) {
            UserPOJO rater = userDAO.getUser(userID);

            rater.removeReviewed(clubID);
        }

        clubDAO.removeClub(clubID);
    }

    public void removeUser(String userID) throws ProfileManagementException {
        UserPOJO removingUser = userDAO.getUser(userID);
        
        if (removingUser == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In USER removal: user not found in database. ID -> " + userID
                )
            );
        }

        // Removal of user ratings
        for ( String clubID : removingUser.getRatedClubs() ) {
            ClubPOJO ratedClub = clubDAO.getClub(clubID);

            ratedClub.removeUserRate(userID);
        }

        // Removal of user reviews
        for ( String clubID : removingUser.getReviewedClubs() ) {
            ClubPOJO reviewedClub = clubDAO.getClub(clubID);

            reviewedClub.removeUserOpinion(userID);
        }

        userDAO.removeUser(userID);
    }

    public void addNewRate(String clubID, int rate, String userID)
            throws ProfileManagementException {
        ClubPOJO ratedClub = clubDAO.getClub(clubID);
        UserPOJO ratingUser = userDAO.getUser(userID);

        if (ratedClub == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In RATE adding: rated club not found in database. ID -> " + clubID
                )
            );
        }

        if (ratingUser == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In RATE adding: rating user not found in database. ID -> " + userID
                )
            );
        }

        // Valid rate?
        if ( ! ParsingTool.parseRate(rate) ) {
            throw new ProfileManagementException(
                new DataFormatException(
                    "In RATE adding: not a valid user rate -> " + rate
                )
            );
        }

        ratedClub.addUserRate(userID, rate);
        ratingUser.addRated(clubID);
    }

    public void addNewOpinion(String clubID, String opinion, String userID) 
            throws ProfileManagementException {
        ClubPOJO reviewedClub = clubDAO.getClub(clubID);
        UserPOJO reviewingUser = userDAO.getUser(userID);

        if (reviewedClub == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In OPINION adding: reviewed club not found in database. ID -> " + clubID
                )
            );
        }

        if (reviewingUser == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In OPINION adding: reviewing user not found in database. ID -> " + userID
                )
            );
        }

        // Valid?
        if ( ! ParsingTool.parseOpinion(opinion) ) {
            throw new ProfileManagementException(
                new DataFormatException(
                    "In OPINION adding: not a valid user opinion -> " + opinion
                )
            );
        }

        reviewedClub.addUserOpinion(userID, opinion);
        reviewingUser.addReviewed(clubID);
    }

    public void removeUserRate(String clubID, String userID) throws ProfileManagementException {
        ClubPOJO unratedClub = clubDAO.getClub(clubID);
        UserPOJO unratingUser = userDAO.getUser(userID);

        if (unratedClub == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In RATE removal: unrated club not found in database. ID -> " + clubID
                )
            );
        }

        if (unratingUser == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In RATE removal: unrating user not found in database. ID -> " + userID
                )
            );
        }

        unratedClub.removeUserRate(userID);
        unratingUser.removeRated(clubID);
    }

    public void removeUserOpinion(String clubID, String userID) throws ProfileManagementException {
        ClubPOJO unreviewedClub = clubDAO.getClub(clubID);
        UserPOJO unreviewingUser = userDAO.getUser(userID);

        if (unreviewedClub == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In OPINION removal: unreviewed club not found in database. ID -> " + clubID
                )
            );
        }

        if (unreviewingUser == null) {
            throw new ProfileManagementException(
                new NoSuchElementException(
                    "In OPINION removal: unreviewing user not found in database. ID -> " + userID
                )
            );
        }

        unreviewedClub.removeUserRate(userID);
        unreviewingUser.removeRated(clubID);        
    }
}