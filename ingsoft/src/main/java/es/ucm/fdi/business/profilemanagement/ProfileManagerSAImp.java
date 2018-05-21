package es.ucm.fdi.business.profilemanagement;

import java.time.LocalDate;
import java.util.Set;

import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

import org.mindrot.jbcrypt.BCrypt;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.profilemanagement.tools.ClubModifierHelper;
import es.ucm.fdi.business.profilemanagement.tools.UserModifierHelper;
import es.ucm.fdi.business.util.ParsingToolHelper;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.data.ReviewPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.Location;
import es.ucm.fdi.integration.data.UserPOJO;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to be used as the Profile Manager of the application. It implements the
 * functionality of {@link ProfileManagerSA}.
 * 
 * @version 08.05.2018
 */
public class ProfileManagerSAImp implements ProfileManagerSA {

	private static ClubDAO clubDAO;
	private static UserDAO userDAO;

	/**
	 * <p>
	 * Builds a {@link ProfileManagerSAImp ProfileManager} whose funcionality
	 * is:
	 * </p>
	 * <p>
	 * 1) To add and remove {@code UserPOJO}s and {@code ClubPOJO}s.
	 * </p>
	 * <p>
	 * 2) To modify {@code UserPOJO}s and {@code ClubPOJO}s inner attributes.
	 * </p>
	 * <p>
	 * 3) To handle users feedback of clubs with {@code ReviewPOJO}s.
	 * </p>
	 * 
	 * @param clubs
	 *            - the app {@code ClubDAO} database
	 * @param users
	 *            - the app {@code UserDAO} database
	 */
	public ProfileManagerSAImp(ClubDAO clubs, UserDAO users) {
		clubDAO = clubs;
		userDAO = users;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param clubID
	 *            {@inheritDoc}
	 * @param name
	 *            {@inheritDoc}
	 * @param address
	 *            {@inheritDoc}
	 * @param price
	 *            {@inheritDoc}
	 * @param tags
	 *            {@inheritDoc}
	 */
	public void addNewClub(String clubID, String name, String address,
			float price, Set<TagPOJO> tags) throws IllegalArgumentException,
			DataFormatException {

		// Is already registered?
		if (clubDAO.exists(clubID)) {

			throw new IllegalArgumentException("In CLUB creation:"
					+ "clubID is already registered -> " + clubID);
		}

		// Arguments are checked
		if (!ParsingToolHelper.parseID(clubID)) {
			throw new DataFormatException("In CLUB creation: "
					+ "not a valid ID format -> " + clubID);
		}

		if (!ParsingToolHelper.parseCommercialName(name)) {
			throw new DataFormatException("In CLUB creation: "
					+ "not a valid commercial name format -> " + name);
		}

		if (!ParsingToolHelper.parseAddress(address)) {
			throw new DataFormatException("In CLUB creation: "
					+ "not a valid address format -> " + address);
		}

		if (!ParsingToolHelper.parsePrice(price)) {
			throw new DataFormatException("In CLUB creation: "
					+ "not a valid price -> " + price);
		}

		/*
		XXX ¿Cómo implementar con TagManager?
		for (TagPOJO tp : tags) {
			if (!tagManager.hasTag(tp)) {
				throw new DataFormatException("In CLUB creation: " +
				"tag not found in valid tags list -> " + tp.getTag()); 
			}
		}
		*/

		// Club creation and addition to database.
		ClubPOJO newClub = new ClubPOJO(clubID, name, address, price, tags);
		clubDAO.addClub(newClub);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param club
	 *            {@inheritDoc}
	 */
	public void addNewClub(ClubPOJO club) throws IllegalArgumentException,
			DataFormatException {

		// Is already registered?
		if (clubDAO.exists(club.getID())) {
			throw new IllegalArgumentException("In CLUB creation:"
					+ "clubID is already registered -> " + club.getID());
		}

		// Arguments are checked
		if (!ParsingToolHelper.parseID(club.getID())) {
			throw new DataFormatException("In CLUB creation: "
					+ "not a valid ID format -> " + club.getID());
		}

		if (!ParsingToolHelper.parseCommercialName(club.getCommercialName())) {
			throw new DataFormatException("In CLUB creation: "
					+ "not a valid commercial name format -> "
					+ club.getCommercialName());
		}

		if (!ParsingToolHelper.parseAddress(club.getAddress())) {
			throw new DataFormatException("In CLUB creation: "
					+ "not a valid address format -> " + club.getAddress());
		}

		if (!ParsingToolHelper.parsePrice(club.getPrice())) {
			throw new DataFormatException("In CLUB creation: "
					+ "not a valid price -> " + club.getPrice());
		}

		/*
		XXX ¿Cómo implementar con TagManager?
		for (TagPOJO tp : club.getTags()) {
			if (!tagManager.hasTag(tp)) {
				throw new DataFormatException("In CLUB creation: " +
				"tag not found in valid tags list -> " + tp.getTag()); 
			}
		}
		*/

		// Addition to database.
		clubDAO.addClub(club);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param userID
	 *            {@inheritDoc}
	 * @param username
	 *            {@inheritDoc}
	 * @param password
	 *            {@inheritDoc}
	 * @param email
	 *            {@inheritDoc}
	 * @param name
	 *            {@inheritDoc}
	 * @param birthday
	 *            {@inheritDoc}
	 */
	public void addNewUser(String userID, String username, String password,
			String email, String name, LocalDate birthday)
			throws IllegalArgumentException, DataFormatException {

		// Is already registered?
		if (userDAO.exists(userID)) {
			throw new IllegalArgumentException("In USER creation: "
					+ "userID is already registered -> " + userID);
		}

		// Arguments are checked
		if (!ParsingToolHelper.parseID(userID)) {
			throw new DataFormatException("In USER creation: "
					+ "not a valid ID format -> " + userID);
		}

		if (!ParsingToolHelper.parseUsername(username)) {
			throw new DataFormatException("In USER creation: "
					+ "not a valid username format -> " + username);
		}

		if (!ParsingToolHelper.parsePassword(password)) {
			throw new DataFormatException("In USER creation: "
					+ "not a valid password format -> " + password);
		}

		if (!ParsingToolHelper.parseEmail(email)) {
			throw new DataFormatException("In USER creation: "
					+ "not a valid email format -> " + email);
		}

		if (!ParsingToolHelper.parseName(name)) {
			throw new DataFormatException("In USER creation: "
					+ "not a valid name format -> " + name);
		}

		if (!ParsingToolHelper.parseBirthday(birthday)) {
			throw new DataFormatException("In USER creation: "
					+ "not a valid birth date -> " + birthday);
		}

		// Password protection
		String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		// User creation and addition
		UserPOJO newUser = new UserPOJO(userID, username, hashPassword, email,
				name, birthday);
		userDAO.addUser(newUser);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param user
	 *            {@inheritDoc}
	 */
	public void addNewUser(UserPOJO user) throws IllegalArgumentException,
			DataFormatException {

		// Is already registered?
		if (userDAO.exists(user.getID())) {
			throw new IllegalArgumentException("In USER creation: "
					+ "userID is already registered -> " + user.getID());
		}

		// Arguments are checked
		if (!ParsingToolHelper.parseID(user.getID())) {
			throw new DataFormatException("In USER creation: "
					+ "not a valid ID format -> " + user.getID());
		}

		if (!ParsingToolHelper.parseUsername(user.getUsername())) {
			throw new DataFormatException("In USER creation: "
					+ "not a valid username format -> " + user.getUsername());
		}

		if (!ParsingToolHelper.parseEmail(user.getEmail())) {
			throw new DataFormatException("In USER creation: "
					+ "not a valid email format -> " + user.getEmail());
		}

		if (!ParsingToolHelper.parseName(user.getName())) {
			throw new DataFormatException("In USER creation: "
					+ "not a valid name format -> " + user.getName());
		}

		if (!ParsingToolHelper.parseBirthday(user.getBirthday())) {
			throw new DataFormatException("In USER creation: "
					+ "not a valid birth date -> " + user.getBirthday());
		}

		// Addition to database.
		userDAO.addUser(user);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param clubID
	 *            {@inheritDoc}
	 * @param dataType
	 *            {@inheritDoc}
	 * @param newData
	 *            {@inheritDoc}
	 */
	public void modifyClubData(String clubID, ClubModifierHelper dataType,
			Object newData) throws NoSuchElementException,
			IllegalArgumentException, DataFormatException {

		ClubPOJO club = clubDAO.getClub(clubID);

		if (club == null) {
			throw new NoSuchElementException("In CLUB modification: "
					+ "club not found in database. ID -> " + clubID);
		}

		// Valid newData?
		try {
			dataType.modify(club, newData);
		} catch (IllegalArgumentException ilegArg) {
			throw ilegArg;
		} catch (DataFormatException dataForm) {
			throw dataForm;
		}
		
		clubDAO.removeClub(clubID);
		clubDAO.addClub(club);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param userID
	 *            {@inheritDoc}
	 * @param dataType
	 *            {@inheritDoc}
	 * @param newData
	 *            {@inheritDoc}
	 */
	public void modifyUserData(String userID, UserModifierHelper dataType,
			Object newData) throws IllegalArgumentException,
			DataFormatException {

		UserPOJO user = userDAO.getUser(userID);

		if (user == null) {
			throw new NoSuchElementException("In USER modification: "
					+ "user not found in database. ID -> " + userID);
		}

		// Valid newData?
		try {
			dataType.modify(user, newData);
		} catch (IllegalArgumentException ilegArg) {
			throw ilegArg;
		} catch (DataFormatException dataForm) {
			throw dataForm;
		}
		userDAO.removeUser(userID);
		userDAO.addUser(user);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param clubID
	 *            {@inheritDoc}
	 */
	public void removeClub(String clubID) throws NoSuchElementException {
		ClubPOJO removingClub = clubDAO.getClub(clubID);

		if (removingClub == null) {
			throw new NoSuchElementException("In CLUB removal: "
					+ "club not found in database. ID -> " + clubID);
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
	 * @param userID
	 *            {@inheritDoc}
	 */
	public void removeUser(String userID) throws NoSuchElementException {
		UserPOJO removingUser = userDAO.getUser(userID);

		if (removingUser == null) {
			throw new NoSuchElementException("In USER removal: "
					+ "user not found in database. ID -> " + userID);
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
	 * @param clubID
	 *            {@inheritDoc}
	 * @param review
	 *            {@inheritDoc}
	 * @param userID
	 *            {@inheritDoc}
	 */
	public void addReview(String clubID, ReviewPOJO review, String userID)
			throws NoSuchElementException, DataFormatException {
		ClubPOJO reviewedClub = clubDAO.getClub(clubID);
		UserPOJO reviewingUser = userDAO.getUser(userID);

		if (reviewedClub == null) {
			throw new NoSuchElementException("In REVIEW adding: "
					+ "reviewed club not found in database. ID -> " + clubID);
		}

		if (reviewingUser == null) {
			throw new NoSuchElementException("In REVIEW adding: "
					+ "reviewing user not found in database. ID -> " + userID);
		}

		// Valid?
		if (!ParsingToolHelper.parseReview(review)) {
			throw new DataFormatException("In REVIEW adding: "
					+ "not a valid user review -> " + review);
		}

		reviewedClub.addUserReview(userID, review);
		reviewingUser.addToReviewed(clubID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param clubID
	 *            {@inheritDoc}
	 * @param userID
	 *            {@inheritDoc}
	 */
	public void removeReview(String clubID, String userID)
			throws NoSuchElementException {
		ClubPOJO unreviewedClub = clubDAO.getClub(clubID);
		UserPOJO unreviewingUser = userDAO.getUser(userID);

		if (unreviewedClub == null) {
			throw new NoSuchElementException("In OPINION removal: "
					+ "unreviewed club not found in database. ID -> " + clubID);
		}

		if (unreviewingUser == null) {
			throw new NoSuchElementException("In OPINION removal:"
					+ "unreviewing user not found in database. ID -> " + userID);
		}

		unreviewedClub.removeUserReview(userID);
		unreviewingUser.removeFromReviewed(clubID);
	}

	/**
	 * 
	 * @param clubID
	 * @return club commercial name
	 */
	public String getCommercialName(String clubID) {
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

		if (clubToManage == null) {
			throw new NoSuchElementException("In CLUB modification: "
					+ "club not found in database. ID -> " + clubID);
		}
		return clubToManage.getCommercialName();
	}

	/**
	 * 
	 * @param clubID
	 * @return club adress
	 */
	public String getClubAdress(String clubID) {
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

		if (clubToManage == null) {
			throw new NoSuchElementException("In CLUB modification:"
					+ " club not found in database. ID -> " + clubID);
		}
		return clubToManage.getAddress();
	}

	/**
	 * 
	 * @param clubID
	 * @return club price
	 */
	public float getClubPrice(String clubID) {
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

		if (clubToManage == null) {
			throw new NoSuchElementException("In CLUB modification: "
					+ "club not found in database. ID -> " + clubID);
		}

		return clubToManage.getPrice();
	}

	/**
	 * 
	 * @param clubID
	 * @return club location
	 */
	public Location getClubLocation(String clubID) {
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

		if (clubToManage == null) {
			throw new NoSuchElementException("In CLUB modification: "
					+ "club not found in database. ID -> " + clubID);
		}

		return clubToManage.getLocation();
	}

	/**
	 * 
	 * @param clubID
	 * @return club rating
	 */
	public float getClubRating(String clubID) {
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

		if (clubToManage == null) {
			throw new NoSuchElementException("In CLUB modification: "
					+ "club not found in database. ID -> " + clubID);
		}
		return clubToManage.getRating();
	}

	/**
	 * 
	 * @param clubID
	 * @return club tags list
	 */
	public List<TagPOJO> getClubTags(String clubID) {
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

		if (clubToManage == null) {
			throw new NoSuchElementException("In CLUB modification: "
					+ "club not found in database. ID -> " + clubID);
		}
		List<TagPOJO> list = new ArrayList<TagPOJO>();
		list.addAll(clubToManage.getTags());
		return list;
	}

	/**
	 * 
	 * @param userID
	 * @return user nickname
	 */
	public String getUserNickname(String userID) {
		UserPOJO userToManage = userDAO.getUser(userID);

		if (userToManage == null) {
			throw new NoSuchElementException("In USER modification: "
					+ "user not found in database. ID -> " + userID);
		}
		return userToManage.getUsername();
	}

	/**
	 * 
	 * @param userID
	 * @return user password
	 */
	public String getUserPassword(String userID) {
		UserPOJO userToManage = userDAO.getUser(userID);

		if (userToManage == null) {
			throw new NoSuchElementException("In USER modification: "
					+ "user not found in database. ID -> " + userID);
		}
		return userToManage.getHashedPassword();
	}

	/**
	 * 
	 * @param userID
	 * @return user email
	 */
	public String getUserEmail(String userID) {
		UserPOJO userToManage = userDAO.getUser(userID);

		if (userToManage == null) {
			throw new NoSuchElementException("In USER modification: "
					+ "user not found in database. ID -> " + userID);
		}
		return userToManage.getEmail();
	}

	/**
	 * 
	 * @param userID
	 * @return user name
	 */
	public String getUserName(String userID) {
		UserPOJO userToManage = userDAO.getUser(userID);

		if (userToManage == null) {
			throw new NoSuchElementException("In USER modification:"
					+ " user not found in database. ID -> " + userID);
		}
		return userToManage.getName();
	}

	/**
	 * 
	 * @param userID
	 * @return user birthday
	 */
	public LocalDate getUserBirthday(String userID) {
		UserPOJO userToManage = userDAO.getUser(userID);

		if (userToManage == null) {
			throw new NoSuchElementException("In USER modification: "
					+ "user not found in database. ID -> " + userID);
		}
		return userToManage.getBirthday();
	}

	/**
	 * 
	 * @param userID
	 * @return clubs reviewed by a given user
	 */
	public List<String> getClubsReviewed(String userID) {
		UserPOJO userToManage = userDAO.getUser(userID);

		if (userToManage == null) {
			throw new NoSuchElementException("In USER modification: "
					+ "user not found in database. ID -> " + userID);
		}

		List<String> list = new ArrayList<String>();
		list.addAll(userToManage.getReviewedClubs());
		return list;
	}

	/**
	 * 
	 * @param clubID
	 * @return the reviews list of a given club
	 */
	public List<ReviewPOJO> getReviewsFromClub(String clubID) {
		ClubPOJO clubToManage = clubDAO.getClub(clubID);

		if (clubToManage == null) {
			throw new NoSuchElementException("In CLUB modification: "
					+ "club not found in database. ID -> " + clubID);
		}

		List<ReviewPOJO> list = new ArrayList<ReviewPOJO>();
		list.addAll(clubToManage.getReviews().values());
		return list;
	}
}