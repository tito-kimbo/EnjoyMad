package es.ucm.fdi.business.ProfileManagement;

import java.time.LocalDate;
import java.util.Set;

import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

import org.mindrot.jbcrypt.BCrypt;

import es.ucm.fdi.business.util.ParsingToolBO;

import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;
import es.ucm.fdi.integration.data.Location;


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

    
    
    
    
    
    
    
    
    
    /**
     * {@inheritDoc}
     * 
     * @param clubID {@inheritDoc}
     * @param name {@inheritDoc}
     * @param address {@inheritDoc}
     * @param price {@inheritDoc}
     * @param tags {@inheritDoc}
     */
    public void addNewClub(String clubID, String name, String address, float price, Set<String> tags) 
            throws IllegalArgumentException, DataFormatException {
        // Is already registered?
        if (clubDAO.exist(clubID)) {
            throw new IllegalArgumentException(
                "In CLUB creation: clubID is already registered -> " + clubID
            ); 
        }

        // Valid arguments? If not -> Exception throwing
        if ( ! ParsingToolBO.parseID(clubID) ) {
            throw new DataFormatException(
                "In CLUB creation: not a valid ID format -> " + clubID
            );
        }
       
        if ( ! ParsingToolBO.parseCommercialName(name) ) {
            throw new DataFormatException(
                "In CLUB creation: not a valid commercial name format -> " + name
            );
        }
        
        if ( ! ParsingToolBO.parseAddress(address) ) {
            throw new DataFormatException(
                "In CLUB creation: not a valid address format -> " + address
            );
        }
        
        if ( ! ParsingToolBO.parsePrice(price) ) {
            throw new DataFormatException(
                "In CLUB creation: not a valid price -> " + price
            );
        }
        
        if ( ! ParsingToolBO.parseTags(tags) ) {
            String tagsInfo = "";
            for (String t : tags) {
                tagsInfo += t + " ";
            }
            
            throw new DataFormatException(
                "In CLUB creation: not a valid set of tags -> " + tagsInfo
            );
        }

        ClubPOJO newClub = new ClubPOJO(clubID, name, address, price, tags);
        clubDAO.addClub(newClub);
    }

    
    
    
    
    
    
    
    
    
    /**
     * {@inheritDoc}
     * 
     * @param userID {@inheritDoc}
     * @param username {@inheritDoc}
     * @param password {@inheritDoc}
     * @param email {@inheritDoc}
     * @param name {@inheritDoc}
     * @param birthday {@inheritDoc}
     */
    public void addNewUser(String userID, String username, String password, String email, String name, LocalDate birthday) 
            throws IllegalArgumentException, DataFormatException {
        // Is already registered?
        if (userDAO.exist(userID)) {
            throw new IllegalArgumentException(
                "In USER creation: userID is already registered -> " + userID
            );
        }

        // Valid arguments? If not -> Exception throwing
        if ( ! ParsingToolBO.parseID(userID) ) {
            throw new DataFormatException(
                "In USER creation: not a valid ID format -> " + userID
            );
        }
       
        if ( ! ParsingToolBO.parseUsername(username) ) {
            throw new DataFormatException(
                "In USER creation: not a valid username format -> " + username
            );
        }
      
        if ( ! ParsingToolBO.parsePassword(password) ) {
            throw new DataFormatException(
                "In USER creation: not a valid password format -> " + password
            );
        }
      
        if ( ! ParsingToolBO.parseEmail(email) ) {
            throw new DataFormatException(
                "In USER creation: not a valid email format -> " + email
            );
        }
      
        if ( ! ParsingToolBO.parseName(name) ) {
            throw new DataFormatException(
                "In USER creation: not a valid name format -> " + name
            );
        }
      
        if ( ! ParsingToolBO.parseBirthday(birthday) ) {
            throw new DataFormatException(
                "In USER creation: not a valid birth date -> " + birthday.toString()
            );
        }

        // Password protection.
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        UserPOJO newUser = new UserPOJO(userID, username, hashPassword, email, name, birthday);
        userDAO.addUser(newUser);
    }










    /**
     * {@inheritDoc}
     * 
     * @param newID {@inheritDoc}
     * @param clubID {@inheritDoc}
     */
    public void modifyClubID(String newID, String clubID) throws NoSuchElementException, DataFormatException {
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }
        
        if ( ! ParsingToolBO.parseID(newID) ) {
			throw new DataFormatException(
                "In ID modification: not a valid ID format -> " + newID
            );
		}

		// Map change
		clubDAO.removeClub(clubToManage.getID());
		clubToManage.setID(newID);
		clubDAO.addClub(clubToManage);
	}

    /**
     * {@inheritDoc}
     * 
     * @param newCommercialName {@inheritDoc}
     * @param clubID {@inheritDoc}
     */
    public void modifyClubCommercialName(String newCommercialName, String clubID) throws NoSuchElementException, DataFormatException {
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }

        if ( ! ParsingToolBO.parseCommercialName(newCommercialName) ) {
			throw new DataFormatException(
					"In COMMERCIAL NAME modification: not a valid commercial name format -> " + newCommercialName
            );
		}

		clubToManage.setCommercialName(newCommercialName);
	}

    /**
     * {@inheritDoc}
     * 
     * @param newAddress {@inheritDoc}
     * @param clubID {@inheritDoc}
     */
    public void modifyClubAddress(String newAddress, String clubID) throws NoSuchElementException, DataFormatException {
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }
        
        if ( ! ParsingToolBO.parseAddress(newAddress) ) {
			throw new DataFormatException(
				"In ADDRESS modification: not a valid address format -> " + newAddress
				);
		}

		clubToManage.setAddress(newAddress);

        // New location
	}

    /**
     * {@inheritDoc}
     * 
     * @param newPrice {@inheritDoc}
     * @param clubID {@inheritDoc}
     */
    public void modifyClubPrice(Float newPrice, String clubID) throws NoSuchElementException, DataFormatException {
        ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }

        if ( ! ParsingToolBO.parsePrice(newPrice) ) {
            throw new DataFormatException(
                "In PRICE modification: not a valid price -> " + newPrice
            );
        }

        clubToManage.setPrice(newPrice);
    }

    /**
     * {@inheritDoc}
     * 
     * @param newLocation {@inheritDoc}
     * @param clubID {@inheritDoc}
     */
    public void modifyClublocation(Location newLocation, String clubID) throws NoSuchElementException {
	    ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }

        // Location parsing?
	
	    clubToManage.setLocation(newLocation);
	}

    /**
     * {@inheritDoc}
     * 
     * @param newRating {@inheritDoc}
     * @param clubID {@inheritDoc}
     */
    public void modifyClubRating(Float newRating, String clubID) throws NoSuchElementException, DataFormatException {
        ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }
        
        if ( ! ParsingToolBO.parseRating(newRating) ) {
            throw new DataFormatException(
                "In RATING modification: not a valid rating -> " + newRating
            );
        }

        clubToManage.setRating(newRating);
    } 

    /**
     * {@inheritDoc}
     * 
     * @param newTag {@inheritDoc}
     * @param clubID {@inheritDoc}
     */
    public void addClubTag(String newTag, String clubID) throws NoSuchElementException, DataFormatException {
        ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }

        if ( ! ParsingToolBO.tagChecker.matcher(newTag).matches() ) {
            throw new DataFormatException(
                "In TAG adding: not a valid tag format -> " + newTag
                );
        }

        clubToManage.addTag(newTag);
    }

    /**
     * {@inheritDoc}
     * 
     * @param tagToRemove {@inheritDoc}
     * @param clubID {@inheritDoc}
     */
    public void removeClubTag(String tagToRemove, String clubID) throws NoSuchElementException, DataFormatException {
        ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }
        
        if ( ! ParsingToolBO.tagChecker.matcher(tagToRemove).matches() ) {
            throw new DataFormatException(
                "In TAG removal: not a valid tag format -> " + tagToRemove
            );
        }

        clubToManage.removeTag(tagToRemove);
    }

    /**
     * {@inheritDoc}
     * 
     * @param clubID {@inheritDoc}
     */
    public void clearClubTags(String clubID) throws NoSuchElementException {
        ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }
        
        clubToManage.clearTags();
    }

   
   
   
   
   
   
   
   
   
    /**
     * {@inheritDoc}
     * 
     * @param newID {@inheritDoc}
     * @param userID {@inheritDoc}
     */
    public void modifyUserID(String newID, String userID) throws NoSuchElementException, DataFormatException {
        UserPOJO userToManage = userDAO.getUser(userID);

        if (userToManage == null) {
            throw new NoSuchElementException(
                "In USER modification: club not found in database. ID -> " + userID
            );
        }

        if ( ! ParsingToolBO.parseID(newID) ) {
            throw new DataFormatException(
                "In ID modification: not a valid ID format -> " + newID
            );
        }

        // Map change
        userDAO.removeUser(userToManage.getID());
        userToManage.setID(newID);
        userDAO.addUser(userToManage);
    }

    /**
     * {@inheritDoc}
     * 
     * @param newUsername {@inheritDoc}
     * @param userID {@inheritDoc}
     */
    public void modifyUserUsername(String newUsername, String userID) throws NoSuchElementException, DataFormatException {
		UserPOJO userToManage = userDAO.getUser(userID);

        if (userToManage == null) {
            throw new NoSuchElementException(
                "In USER modification: club not found in database. ID -> " + userID
            );
        }

        if ( ! ParsingToolBO.parseUsername(newUsername) ) {
            throw new DataFormatException(
                "In USERNAME modification: not a valid username format -> " + newUsername
            );
        }

        userToManage.setUsername(newUsername);
    }

    /**
     * {@inheritDoc}
     * 
     * @param newPassword {@inheritDoc}
     * @param userID {@inheritDoc}
     */
    public void modifyUserPassWord(String newPassword, String userID) throws NoSuchElementException, DataFormatException {
        UserPOJO userToManage = userDAO.getUser(userID);

        if (userToManage == null) {
            throw new NoSuchElementException(
                "In USER modification: club not found in database. ID -> " + userID
            );
        }

        if ( ! ParsingToolBO.parsePassword(newPassword) ) {
            throw new DataFormatException(
                "In PASSWORD modification: not a valid password format -> " + newPassword
            );
        }

        // Password protection.
        String hashPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        userToManage.setPassword(hashPassword);
    }

    /**
     * {@inheritDoc}
     * 
     * @param newWmail {@inheritDoc}
     * @param userID {@inheritDoc}
     */
    public void modifyUserEmail(String newEmail, String userID) throws NoSuchElementException, DataFormatException {
        UserPOJO userToManage = userDAO.getUser(userID);

        if (userToManage == null) {
            throw new NoSuchElementException(
                "In USER modification: club not found in database. ID -> " + userID
            );
        }

        if ( ! ParsingToolBO.parseEmail(newEmail) ) {
            throw new DataFormatException(
                "In EMAIL modification: not a valid email format -> " + newEmail
            );
        }

        userToManage.setEmail(newEmail);
    }

    /**
     * {@inheritDoc}
     * 
     * @param newName {@inheritDoc}
     * @param userID {@inheritDoc}
     */
    public void modifyUserName(String newName, String userID) throws NoSuchElementException, DataFormatException {
        UserPOJO userToManage = userDAO.getUser(userID);

        if (userToManage == null) {
            throw new NoSuchElementException(
                "In USER modification: club not found in database. ID -> " + userID
            );
        }

        if ( ! ParsingToolBO.parseName(newName) ) {
            throw new DataFormatException(
                "In NAME modification: not a valid name format -> " + newName
            );
        }

        userToManage.setName(newName);
    }

    /**
     * {@inheritDoc}
     * 
     * @param newBirthday {@inheritDoc}
     * @param userID {@inheritDoc}
     */
    public void modifyUserBirthday(LocalDate newBirthday, String userID) throws NoSuchElementException, DataFormatException {
        UserPOJO userToManage = userDAO.getUser(userID);

        if (userToManage == null) {
            throw new NoSuchElementException(
                "In USER modification: club not found in database. ID -> " + userID
            );
        }
        
        if ( ! ParsingToolBO.parseBirthday(newBirthday) ) {
            throw new DataFormatException(
                "In BIRTHDAY modification: not a valid birth date -> " + newBirthday.toString()
            );
        }

        userToManage.setBirthday(newBirthday);
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
                "In CLUB removal: club not found in database. ID -> " + clubID
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

    /**
     * {@inheritDoc}
     * 
     * @param userID {@inheritDoc}
     */
    public void removeUser(String userID) throws NoSuchElementException {
        UserPOJO removingUser = userDAO.getUser(userID);
        
        if (removingUser == null) {
            throw new NoSuchElementException(
                "In USER removal: user not found in database. ID -> " + userID
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

    
    
    
    
    
    
    
    
    
    
    /**
     * {@inheritDoc}
     * 
     * @param clubID {@inheritDoc}
     * @param rate {@inheritDoc}
     * @param userID {@inheritDoc}
     */
    public void addNewRate(String clubID, int rate, String userID)
            throws NoSuchElementException, DataFormatException {
        ClubPOJO ratedClub = clubDAO.getClub(clubID);
        UserPOJO ratingUser = userDAO.getUser(userID);

        if (ratedClub == null) {
            throw new NoSuchElementException(
                    "In RATE adding: rated club not found in database. ID -> " + clubID
            );
        }

        if (ratingUser == null) {
            throw new NoSuchElementException(
                "In RATE adding: rating user not found in database. ID -> " + userID
            );
        }

        // Valid rate?
        if ( ! ParsingToolBO.parseRate(rate) ) {
            throw new DataFormatException(
                "In RATE adding: not a valid user rate -> " + rate
            );
        }

        ratedClub.addUserRate(userID, rate);
        ratingUser.addRated(clubID);
    }

    /**
     * {@inheritDoc}
     * 
     * @param clubID {@inheritDoc}
     * @param opinion {@inheritDoc}
     * @param userID {@inheritDoc}
     */
    public void addNewOpinion(String clubID, String opinion, String userID) 
            throws NoSuchElementException, DataFormatException {
        ClubPOJO reviewedClub = clubDAO.getClub(clubID);
        UserPOJO reviewingUser = userDAO.getUser(userID);

        if (reviewedClub == null) {
            throw new NoSuchElementException(
                "In OPINION adding: reviewed club not found in database. ID -> " + clubID
            );
        }

        if (reviewingUser == null) {
            throw new NoSuchElementException(
                "In OPINION adding: reviewing user not found in database. ID -> " + userID
            );
        }

        // Valid?
        if ( ! ParsingToolBO.parseOpinion(opinion) ) {
            throw new DataFormatException(
                "In OPINION adding: not a valid user opinion -> " + opinion
            );           
        }

        reviewedClub.addUserOpinion(userID, opinion);
        reviewingUser.addReviewed(clubID);
    }

    public void removeUserRate(String clubID, String userID) throws NoSuchElementException {
        ClubPOJO unratedClub = clubDAO.getClub(clubID);
        UserPOJO unratingUser = userDAO.getUser(userID);

        if (unratedClub == null) {
            throw new NoSuchElementException(
                "In RATE removal: unrated club not found in database. ID -> " + clubID
            );
        }

        if (unratingUser == null) {
            throw new NoSuchElementException(
                "In RATE removal: unrating user not found in database. ID -> " + userID
            );
        }

        unratedClub.removeUserRate(userID);
        unratingUser.removeRated(clubID);
    }

    public void removeUserOpinion(String clubID, String userID) throws NoSuchElementException {
        ClubPOJO unreviewedClub = clubDAO.getClub(clubID);
        UserPOJO unreviewingUser = userDAO.getUser(userID);

        if (unreviewedClub == null) {
            throw new NoSuchElementException(
                "In OPINION removal: unreviewed club not found in database. ID -> " + clubID
            );
        }

        if (unreviewingUser == null) {
            throw new NoSuchElementException(
                "In OPINION removal: unreviewing user not found in database. ID -> " + userID
            );
        }

        unreviewedClub.removeUserRate(userID);
        unreviewingUser.removeRated(clubID);        
    }
}