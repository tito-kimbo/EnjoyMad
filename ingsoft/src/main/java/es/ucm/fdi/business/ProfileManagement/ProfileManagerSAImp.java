package es.ucm.fdi.business.ProfileManagement;

import java.util.Set;
import java.time.LocalDate;


import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.UserPOJO;

import es.ucm.fdi.business.ProfileManagement.ManagementTools.*;

import org.mindrot.jbcrypt.BCrypt;


/**
 * Class to be used as the Profile Manager of the application.
 */
public class ProfileManagerSAImp implements ProfileManagerSA {
    private static ClubDAOImp clubDAO = new ClubDAOImp(); //¿Singleton?
    private static UserDAOImp userDAO = new UserDAOImp();

    public ProfileManagerSAImp(ClubDAOImp clubs, UserDAOImp users) {
        clubDAO = clubs;
        userDAO = users;
    }

    public void addNewClub(String clubID, String name, String address, float price, Set<String> tags) {
        // Is already registered?
        if (clubDAO.exist(clubID)) {
            // throw AlreadyExistingClub exception
        }

        // Valid arguments? If not -> Exception throwing
        if ( ! ParsingTool.parseID(clubID) ) {

        }
        if ( ! ParsingTool.parseCommercialName(name) ) {
            
        }
        if ( ! ParsingTool.parseAddress(address) ) {
            
        }
        if ( ! ParsingTool.parsePrice(price) ) {
            
        }
        if ( ! ParsingTool.parseTags(tags) ) {
            
        }

        ClubPOJO newClub = new ClubPOJO(clubID, name, address, price, tags);
        clubDAO.addClub(newClub);
    }

    public void addNewUser(String userID, String username, String password, String email, String name, LocalDate birthday) {
        // Is already registered?
        if (userDAO.exist(userID)) {
            // throw AlreadyExistingUser exception
        }

        // Valid arguments? If not -> Exception throwing
        if ( ! ParsingTool.parseID(userID) ) {

        }
        if ( ! ParsingTool.parseUsername(username) ) {
            
        }
        if ( ! ParsingTool.parsePassword(password) ) {
            
        }
        if ( ! ParsingTool.parseEmail(email) ) {
            
        }
        if ( ! ParsingTool.parseName(name) ) {
            
        }
        if ( ! ParsingTool.parseBirthday(birthday) ) {
            
        }

        // Password protection.
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        UserPOJO newUser = new UserPOJO(userID, username, hashPassword, email, name, birthday);
        userDAO.addUser(newUser);
    }

	public void modifyClubData(String clubID, ClubDataID dataID, Object newData) {
        ClubPOJO club = clubDAO.getClub(clubID);

        if (club == null) {
            // throw NonExistingClub excepction
        }

        // try...catch (for wrong newData)
        ClubManageTool clubManager = new ClubManageTool(club, clubDAO);
        clubManager.modify(dataID, newData);
    }
    
    public void modifyUserData(String userID, UserDataID dataID, Object newData) {
        UserPOJO user = userDAO.getUser(userID);

        if (user == null) {
            // throw NonExistingUser exception //Better use an already existing exception
        }

        // try...catch (for wrong newData)
        UserManageTool userManager = new UserManageTool(user, userDAO);
        userManager.modify(dataID, newData);
    }

	public void removeClub(String clubID) {
        ClubPOJO removingClub = clubDAO.getClub(clubID);

        if (removingClub == null) {
            //throw NonExistingProfile exception
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

    public void removeUser(String userID) {
        UserPOJO removingUser = userDAO.getUser(userID);
        
        if (removingUser == null) {
            // throw NonExistingProfile exception
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

    public void addNewRate(String clubID, int rate, String userID) {
        ClubPOJO ratedClub = clubDAO.getClub(clubID);

        if (ratedClub == null) {
            // throw NonExistingClub excepction
        }

        // Valid?
        if ( ! ParsingTool.parseRate(rate) ) {

        }

        ratedClub.addUserRate(userID, rate);
    }

    public void addNewOpinion(String clubID, String opinion, String userID) {
        ClubPOJO reviewedClub = clubDAO.getClub(clubID);

        if (reviewedClub == null) {
            // throw NonExistingClub exception
        }

        // Parse? [Número máximo de caracteres]

        reviewedClub.addUserOpinion(userID, opinion);
    }

    public void removeUserRate(String clubID, String userID) {
        ClubPOJO unratedClub = clubDAO.getClub(clubID);

        if (unratedClub == null) {
            // throw exception
        }

        // existUser exception ???
        unratedClub.removeUserRate(userID);
    }

    public void removeUserOpinion(String clubID, String userID) {
        ClubPOJO unreviewedClub = clubDAO.getClub(clubID);
        
        if (unreviewedClub == null) {
            // throw exception
        }

        // existUer excp ???
        unreviewedClub.removeUserOpinion(userID);        
    }
}