package es.ucm.fdi.business.profilemanagement;

import java.time.LocalDate;
import java.util.Set;

import java.util.NoSuchElementException;

import org.mindrot.jbcrypt.BCrypt;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.profilemanagement.tools.ClubModifierHelper;
import es.ucm.fdi.business.profilemanagement.tools.UserModifierHelper;
import es.ucm.fdi.business.tagmanagement.TagManagerSA;
import es.ucm.fdi.business.util.ParsingToolHelper;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.data.ReviewPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Class to be used as the Profile Manager of the application. It implements the
 * functionality of {@link ProfileManagerSA}.
 * 
 * @version 08.05.2018
 */
public class ProfileManagerSAImp implements ProfileManagerSA {

	private static ClubModifierHelper[] clubModifications = {
			ClubModifierHelper.COMMERCIAL_NAME, 
			ClubModifierHelper.LOCATION,
			ClubModifierHelper.ADDRESS,
			ClubModifierHelper.PRICE,
			ClubModifierHelper.TAGS
	};

	private static UserModifierHelper[] userModifications = {
			UserModifierHelper.USERNAME,
			UserModifierHelper.PASSWORD,
			UserModifierHelper.EMAIL,
			UserModifierHelper.NAME,
			UserModifierHelper.BIRTHDAY
	};
	
	private static ClubDAO clubDAO;
	private static UserDAO userDAO;
	private static TagManagerSA tagManager;

	/**
	 * <p>
	 * Builds a {@link ProfileManagerSAImp ProfileManager} whose funcionality
	 * is:
	 * </p>
	 * <p>
	 * 1) To add and remove {@code UserPOJO}s and {@code ClubPOJO}s.
	 * </p>
	 * <p>
	 * 2) To parse {@code UserPOJO}s and {@code ClubPOJO}s inner attributes.
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
	public ProfileManagerSAImp(ClubDAO clubs, UserDAO users, TagManagerSA tags) {
		clubDAO = clubs;
		userDAO = users;
		tagManager = tags;
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
			float price, Set<TagPOJO> tags) throws IllegalArgumentException {

		// Is already registered?
		if (clubDAO.exists(clubID)) {

			throw new IllegalArgumentException("In CLUB creation:"
					+ "clubID is already registered -> " + clubID);
		}

		// Arguments are checked
		if (!ParsingToolHelper.parseID(clubID)) {
			throw new IllegalArgumentException("In CLUB creation: "
					+ "not a valid ID format -> " + clubID);
		}

		if (!ParsingToolHelper.parseCommercialName(name)) {
			throw new IllegalArgumentException("In CLUB creation: "
					+ "not a valid commercial name format -> " + name);
		}

		if (!ParsingToolHelper.parseAddress(address)) {
			throw new IllegalArgumentException("In CLUB creation: "
					+ "not a valid address format -> " + address);
		}

		if (!ParsingToolHelper.parsePrice(price)) {
			throw new IllegalArgumentException("In CLUB creation: "
					+ "not a valid price -> " + price);
		}

		for (TagPOJO tp : tags) {
			if (!tagManager.hasTag(tp)) {
				throw new IllegalArgumentException("In CLUB creation: " +
				"tag not found in valid tags list -> " + tp.getTag()); 
			}
		}
		

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
	public void addNewClub(ClubPOJO club) throws IllegalArgumentException {

		// Is already registered?
		if (clubDAO.exists(club.getID())) {
			throw new IllegalArgumentException("In CLUB creation:"
					+ "clubID is already registered -> " + club.getID());
		}

		// Arguments are checked
		if (!ParsingToolHelper.parseID(club.getID())) {
			throw new IllegalArgumentException("In CLUB creation: "
					+ "not a valid ID format -> " + club.getID());
		}

		if (!ParsingToolHelper.parseCommercialName(club.getCommercialName())) {
			throw new IllegalArgumentException("In CLUB creation: "
					+ "not a valid commercial name format -> "
					+ club.getCommercialName());
		}

		if (!ParsingToolHelper.parseAddress(club.getAddress())) {
			throw new IllegalArgumentException("In CLUB creation: "
					+ "not a valid address format -> " + club.getAddress());
		}

		if (!ParsingToolHelper.parsePrice(club.getPrice())) {
			throw new IllegalArgumentException("In CLUB creation: "
					+ "not a valid price -> " + club.getPrice());
		}

		
		for (TagPOJO tp : club.getTags()) {
			if (!tagManager.hasTag(tp)) {
				throw new IllegalArgumentException("In CLUB creation: " +
				"tag not found in valid tags list -> " + tp.getTag()); 
			}
		}
		

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
			throws IllegalArgumentException {

		// Is already registered?
		if (userDAO.exists(userID)) {
			throw new IllegalArgumentException("In USER creation: "
					+ "userID is already registered -> " + userID);
		}

		// Arguments are checked
		if (!ParsingToolHelper.parseID(userID)) {
			throw new IllegalArgumentException("In USER creation: "
					+ "not a valid ID format -> " + userID);
		}

		if (!ParsingToolHelper.parseUsername(username)) {
			throw new IllegalArgumentException("In USER creation: "
					+ "not a valid username format -> " + username);
		}

		if (!ParsingToolHelper.parsePassword(password)) {
			throw new IllegalArgumentException("In USER creation: "
					+ "not a valid password format -> " + password);
		}

		if (!ParsingToolHelper.parseEmail(email)) {
			throw new IllegalArgumentException("In USER creation: "
					+ "not a valid email format -> " + email);
		}

		if (!ParsingToolHelper.parseName(name)) {
			throw new IllegalArgumentException("In USER creation: "
					+ "not a valid name format -> " + name);
		}

		if (!ParsingToolHelper.parseBirthday(birthday)) {
			throw new IllegalArgumentException("In USER creation: "
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
	public void addNewUser(UserPOJO user) throws IllegalArgumentException {

		// Is already registered?
		if (userDAO.exists(user.getID())) {
			throw new IllegalArgumentException("In USER creation: "
					+ "userID is already registered -> " + user.getID());
		}

		// Arguments are checked
		if (!ParsingToolHelper.parseID(user.getID())) {
			throw new IllegalArgumentException("In USER creation: "
					+ "not a valid ID format -> " + user.getID());
		}

		if (!ParsingToolHelper.parseUsername(user.getUsername())) {
			throw new IllegalArgumentException("In USER creation: "
					+ "not a valid username format -> " + user.getUsername());
		}

		if (!ParsingToolHelper.parseEmail(user.getEmail())) {
			throw new IllegalArgumentException("In USER creation: "
					+ "not a valid email format -> " + user.getEmail());
		}

		if (!ParsingToolHelper.parseName(user.getName())) {
			throw new IllegalArgumentException("In USER creation: "
					+ "not a valid name format -> " + user.getName());
		}

		if (!ParsingToolHelper.parseBirthday(user.getBirthday())) {
			throw new IllegalArgumentException("In USER creation: "
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
	 * @param clubChanges
	 *            {@inheritDoc}
	 */
	public void modifyClubData(String clubID, ClubPOJO clubChanges) 
			throws NoSuchElementException, IllegalArgumentException {

		ClubPOJO club = clubDAO.getClub(clubID);

		// Club exists
		if (club == null) {
			throw new NoSuchElementException("In CLUB modification: "
					+ "club not found in database. ID -> " + clubID);
		}

		// ID correspondance
		if (clubID != clubChanges.getID()) {
			throw new NoSuchElementException("In CLUB modification: "
					+ "no correspondance between IDs: " + 
					clubID + " <-> " + clubChanges.getID());
		}

		// Parsing
		try {
			for (ClubModifierHelper mod : clubModifications) {
				mod.parse(club, tagManager, clubChanges);
			}
		}
		catch (IllegalArgumentException ilegArg) {
			throw ilegArg;
		}

		// If everything ok, modifies.
		for (ClubModifierHelper mod : clubModifications) {
			mod.modify(club, clubChanges);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param userID
	 *            {@inheritDoc}
	 * @param userChanges
	 *            {@inheritDoc}
	 */
	public void modifyUserData(String userID, UserPOJO userChanges) 
		throws NoSuchElementException, IllegalArgumentException {

		UserPOJO user = userDAO.getUser(userID);

		// User exists
		if (user == null) {
			throw new NoSuchElementException("In USER modification: "
					+ "user not found in database. ID -> " + userID);
		}

		// ID correspondance
		if (userID != userChanges.getID()) {
			throw new NoSuchElementException("In USER modification: "
					+ "no correspondance between IDs: " + 
					userID + " <-> " + userChanges.getID());
		}

		// Parsing
		try {
			for (UserModifierHelper mod : userModifications) {
				mod.parse(user, userChanges);
			}
		}
		catch (IllegalArgumentException ilegArg) {
			throw ilegArg;
		}

		// If everything ok, modifies.
		for (UserModifierHelper mod : userModifications) {
			mod.modify(user, userChanges);
		}
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
			throws NoSuchElementException, IllegalArgumentException {
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
			throw new IllegalArgumentException("In REVIEW adding: "
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
}