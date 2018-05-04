package es.ucm.fdi.business.profilemanagement;

import java.time.LocalDate;
import java.util.Set;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

import org.mindrot.jbcrypt.BCrypt;

import es.ucm.fdi.business.util.ParsingToolHelper;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.ReviewPOJO;
import es.ucm.fdi.integration.data.UserPOJO;
import es.ucm.fdi.integration.data.Location;


/**
 * Class to be used as the Profile Manager of the application. Its methods can only throw
 * <code>ProfileManagerException</code> exceptions which contain the cause of the exception.
 */
public class ProfileManagerSAImp implements ProfileManagerSA {
    

    
    /**
     * {@inheritDoc}
     * 
     * @param club {@inheritDoc}
     */
    public void addNewClub(ClubPOJO club) 
            throws IllegalArgumentException, DataFormatException {
        // Is already registered?
    	ClubDAO clubDAO = new ClubDAOImp();
        if (clubDAO.exist(club.getID())) {
            throw new IllegalArgumentException(
                "In CLUB creation: clubID is already registered -> " + club.getID()
            ); 
        }

        // Valid arguments? If not -> Exception throwing
        if ( ! ParsingToolHelper.parseID(club.getID()) ) {
            throw new DataFormatException(
                "In CLUB creation: not a valid ID format -> " + club.getID()
            );
        }
       
        if ( ! ParsingToolHelper.parseCommercialName(club.getCommercialName()) ) {
            throw new DataFormatException(
                "In CLUB creation: not a valid commercial name format -> " + club.getCommercialName()
            );
        }
        
        if ( ! ParsingToolHelper.parseAddress(club.getAddress()) ) {
            throw new DataFormatException(
                "In CLUB creation: not a valid address format -> " + club.getAddress()
            );
        }
        
        if ( ! ParsingToolHelper.parsePrice(club.getPrice()) ) {
            throw new DataFormatException(
                "In CLUB creation: not a valid price -> " + club.getPrice()
            );
        }
        
        if ( ! ParsingToolHelper.parseTags(club.getTags()) ) {
            String tagsInfo = "";
            for (String t : club.getTags()) {
                tagsInfo += t + " ";
            }
            
            throw new DataFormatException(
                "In CLUB creation: not a valid set of tags -> " + tagsInfo
            );
        }

        clubDAO.addClub(club);
    }

    
    /**
     * {@inheritDoc}
     * 
     * @param user {@inheritDoc}
     */
    public void addNewUser(UserPOJO user) 
            throws IllegalArgumentException, DataFormatException {
    	UserDAO userDAO = new UserDAOImp();
        // Is already registered?
        if (userDAO.exist(user.getID())) {
            throw new IllegalArgumentException(
                "In USER creation: userID is already registered -> " + user.getID()
            );
        }

        // Valid arguments? If not -> Exception throwing
        if ( ! ParsingToolHelper.parseID(user.getID()) ) {
            throw new DataFormatException(
                "In USER creation: not a valid ID format -> " + user.getID()
            );
        }
       
        if ( ! ParsingToolHelper.parseUsername(user.getUsername()) ) {
            throw new DataFormatException(
                "In USER creation: not a valid username format -> " + user.getUsername()
            );
        }
      
        if ( ! ParsingToolHelper.parsePassword(user.getPassword()) ) {
            throw new DataFormatException(
                "In USER creation: not a valid password format -> " + user.getPassword()
            );
        }
      
        if ( ! ParsingToolHelper.parseEmail(user.getEmail()) ) {
            throw new DataFormatException(
                "In USER creation: not a valid email format -> " + user.getEmail()
            );
        }
      
        if ( ! ParsingToolHelper.parseName(user.getName()) ) {
            throw new DataFormatException(
                "In USER creation: not a valid name format -> " + user.getName()
            );
        }
      
        if ( ! ParsingToolHelper.parseBirthday(user.getBirthday()) ) {
            throw new DataFormatException(
                "In USER creation: not a valid birth date -> " + user.getBirthday()
            );
        }

        // Password protection.
        String hashPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        userDAO.addUser(user);
    }




    /**
     * {@inheritDoc}
     * 
     * @param newCommercialName {@inheritDoc}
     * @param clubID {@inheritDoc}
     */
    public void modifyClubCommercialName(String newCommercialName, String clubID) throws NoSuchElementException, DataFormatException {
    	ClubDAO clubDAO = new ClubDAOImp();
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }

        if ( ! ParsingToolHelper.parseCommercialName(newCommercialName) ) {
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
    	ClubDAO clubDAO = new ClubDAOImp();
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }
        
        if ( ! ParsingToolHelper.parseAddress(newAddress) ) {
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
    	ClubDAO clubDAO = new ClubDAOImp();
        ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }

        if ( ! ParsingToolHelper.parsePrice(newPrice) ) {
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
    	ClubDAO clubDAO = new ClubDAOImp();
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
    	ClubDAO clubDAO = new ClubDAOImp();
        ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }
        
        if ( ! ParsingToolHelper.parseRating(newRating) ) {
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
    	ClubDAO clubDAO = new ClubDAOImp();
        ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }

        if ( ! ParsingToolHelper.tagChecker.matcher(newTag).matches() ) {
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
    	ClubDAO clubDAO = new ClubDAOImp();
        ClubPOJO clubToManage = clubDAO.getClub(clubID);

        if (clubToManage == null) {
            throw new NoSuchElementException(
                "In CLUB modification: club not found in database. ID -> " + clubID
            );
        }
        
        if ( ! ParsingToolHelper.tagChecker.matcher(tagToRemove).matches() ) {
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
    	ClubDAO clubDAO = new ClubDAOImp();
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
     * @param newUsername {@inheritDoc}
     * @param userID {@inheritDoc}
     */
    public void modifyUserUsername(String newUsername, String userID) throws NoSuchElementException, DataFormatException {
    	UserDAO userDAO = new UserDAOImp();
		UserPOJO userToManage = userDAO.getUser(userID);

        if (userToManage == null) {
            throw new NoSuchElementException(
                "In USER modification: club not found in database. ID -> " + userID
            );
        }

        if ( ! ParsingToolHelper.parseUsername(newUsername) ) {
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
    	UserDAO userDAO = new UserDAOImp();
        UserPOJO userToManage = userDAO.getUser(userID);

        if (userToManage == null) {
            throw new NoSuchElementException(
                "In USER modification: club not found in database. ID -> " + userID
            );
        }

        if ( ! ParsingToolHelper.parsePassword(newPassword) ) {
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
    	UserDAO userDAO = new UserDAOImp();
        UserPOJO userToManage = userDAO.getUser(userID);

        if (userToManage == null) {
            throw new NoSuchElementException(
                "In USER modification: club not found in database. ID -> " + userID
            );
        }

        if ( ! ParsingToolHelper.parseEmail(newEmail) ) {
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
    	UserDAO userDAO = new UserDAOImp();
        UserPOJO userToManage = userDAO.getUser(userID);

        if (userToManage == null) {
            throw new NoSuchElementException(
                "In USER modification: club not found in database. ID -> " + userID
            );
        }

        if ( ! ParsingToolHelper.parseName(newName) ) {
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
    	UserDAO userDAO = new UserDAOImp();
    	UserPOJO userToManage = userDAO.getUser(userID);

        if (userToManage == null) {
            throw new NoSuchElementException(
                "In USER modification: club not found in database. ID -> " + userID
            );
        }
        
        if ( ! ParsingToolHelper.parseBirthday(newBirthday) ) {
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
    	ClubDAO clubDAO = new ClubDAOImp();
    	UserDAO userDAO = new UserDAOImp();
        ClubPOJO removingClub = clubDAO.getClub(clubID);

        if (removingClub == null) {
            throw new NoSuchElementException(
                "In CLUB removal: club not found in database. ID -> " + clubID
            );
        }

        // Removal of reviewers (in user attributes)
        for ( String userID : removingClub.getReviewers() ) {
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
    	UserDAO userDAO = new UserDAOImp();
    	ClubDAO clubDAO = new ClubDAOImp();
        UserPOJO removingUser = userDAO.getUser(userID);
        
        if (removingUser == null) {
            throw new NoSuchElementException(
                "In USER removal: user not found in database. ID -> " + userID
            );
        }

        // Removal of user reviews
        for ( String clubID : removingUser.getReviewedClubs() ) {
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
    	ClubDAO clubDAO = new ClubDAOImp();
    	UserDAO userDAO = new UserDAOImp();
        ClubPOJO reviewedClub = clubDAO.getClub(clubID);
        UserPOJO reviewingUser = userDAO.getUser(userID);
        
        if (reviewedClub == null) {
            throw new NoSuchElementException(
                "In REVIEW adding: reviewed club not found in database. ID -> " + clubID
            );
        }

        if (reviewingUser == null) {
            throw new NoSuchElementException(
                "In REVIEW adding: reviewing user not found in database. ID -> " + userID
            );
        }

        // Valid?
        if ( ! ParsingToolHelper.parseReview(review) ) {
            throw new DataFormatException(
                "In REVIEW adding: not a valid user review -> " + review
            );           
        }

        reviewedClub.addUserReview(userID, review);
        reviewingUser.addToReviewed(clubID);
    }

    public void removeReview(String clubID, String userID) throws NoSuchElementException {
    	ClubDAO clubDAO = new ClubDAOImp();
    	UserDAO userDAO = new UserDAOImp();
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

        unreviewedClub.removeUserReview(userID);    
    }

}