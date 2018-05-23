package es.ucm.fdi.integration.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.ucm.fdi.business.profilemanagement.ProfileManagerSA;

/**
 * Class that contains representative information of a certain user.
 * 
 * @version 21.05.2018
 */
public class UserPOJO extends DataPOJO implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 1385462895190091593L;
	String username, hashedPassword, email, name; 
	LocalDate birthday;

	/**
	 * Set of reviewed clubs IDs.
	 */
	private Set<String> reviewedClubs;

	/**
	 * List of favorite clubs.
	 * XXX ¿Debería ser una List<String> con los IDs?
	 */
	private List<ClubPOJO> preferencesList = new ArrayList<ClubPOJO>();

	/**
	 * TODO Comentar
	 */
	private Map<TagPOJO, Integer> valueTags = new HashMap<TagPOJO, Integer>();

	/**
	 * User class normal constructor
	 * 
	 * @param id       user id
	 * @param username user username (login)
	 * @param pass     user password (login)
	 * @param email    user email
	 * @param name     user name
	 * @param bday     user birthday date
	 */
	public UserPOJO(String id, String username, String pass, String email, String name, LocalDate bday) {
		super(id);
		this.username = username;
		this.hashedPassword = pass;
		this.email = email;
		this.name = name;
		this.birthday = bday;

		reviewedClubs = new HashSet<String>();
	}

	/**
	 * User class whole constructor (for testing).
	 * 
	 * @param id       user id
	 * @param username user username (login)
	 * @param pass     user password (login)
	 * @param email    user email
	 * @param name     user name
	 * @param bday     user birthday date
	 * @param reviews  user reviewed clubs
	 */
	public UserPOJO(String id, String username, String pass, String email, String name, LocalDate bday,
			Set<String> reviews) {
		super(id);
		this.username = username;
		this.hashedPassword = pass;
		this.email = email;
		this.name = name;
		this.birthday = bday;
		this.reviewedClubs = new HashSet<String>(reviews);
	}

	/**
	 * Returns the username.
	 * 
	 * @return username (login)
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns a {@code String} hashed from the user login password.
	 * 
	 * @return password
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * Sets the hashed password for user login.
	 * 
	 * @param hashedPassword {@code String} hashed from password used in login
	 */
	public void setHashedPassword(String password) {
		this.hashedPassword = password;
	}

	/**
	 * Returns the email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the birthday
	 * 
	 * @return birthday
	 */
	public LocalDate getBirthday() {
		return birthday;
	}

	/**
	 * Sets the birthday
	 * 
	 * @param birthday
	 */
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	/**
	 * Returns a Collection of reviewed clubs IDs.
	 * 
	 * @return 	{@code Collection<String>} with IDs of clubs 
	 * 			reviewed by the user
	 * 
	 * @see ProfileManagerSA#removeUser(String)
	 */
	public Collection<String> getReviewedClubs() {
		return (Collection<String>) reviewedClubs;
	}

	/**
	 * Adds a club to the reviewed clubs set.
	 * 
	 * @param clubID reviewed club id
	 */
	public void addToReviewed(String clubID) {
		reviewedClubs.add(clubID);
	}

	/**
	 * Removes a club from the reviewed clubs set.
	 * 
	 * @param clubID unreviewed club id
	 */
	public void removeFromReviewed(String clubID) {
		reviewedClubs.remove(clubID);
	}
	
	/**
	 * TODO Comentar
	 */
	public List<ClubPOJO> getPreferencesList() {
		return preferencesList;
	}

	/**
	 * TODO Comentar
	 */
	public void setPreferencesList(List<ClubPOJO> preferencesList) {
		this.preferencesList = preferencesList;
	}

	/**
	 * TODO Comentar
	 */
	public Map<TagPOJO, Integer> getValueTags() {
		return valueTags;
	}

	/**
	 * TODO Comentar
	 */
	public void setValueTags(Map<TagPOJO, Integer> valueTags) {
		this.valueTags = valueTags;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof UserPOJO))
			return false;
		
		boolean equalTags, equalReviews, equalPreferences;
		
		equalTags = valueTags.equals(((UserPOJO) obj).valueTags);
		equalPreferences = preferencesList.equals(((UserPOJO) obj).preferencesList);
		equalReviews = reviewedClubs.equals(((UserPOJO) obj).reviewedClubs);
		
		System.out.println("TAGS: " + equalTags + " PREFERENCES: " + equalPreferences);
		System.out.println("REVIEWS: " + equalReviews);
		System.out.println("PASSWORDS: " + hashedPassword.equals(((UserPOJO) obj).hashedPassword));
		
		return	birthday.equals(((UserPOJO) obj).birthday)
				&& name.equals(((UserPOJO) obj).name)
				&& hashedPassword.equals(((UserPOJO) obj).hashedPassword)
				&& getID().equals(((UserPOJO) obj).getID())
				&& username.equals(((UserPOJO) obj).username)
				&& equalTags
				&& equalPreferences
				&& equalReviews;
	}
}