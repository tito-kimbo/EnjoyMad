package es.ucm.fdi.integration.data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class UserPOJO extends DataPOJO {
	String username, password, email, name; 
	LocalDate birthday;

	/**
	 * Set of reviewed clubs IDs.
	 */
	Set<String> reviewedClubs;

	/**
	 * User class normal constructor
	 * @param id user id
	 * @param pass user password
	 * @param email user email
	 * @param name user name
	 * @param bday user birthday date
	 * @param rates user rated clubs
	 * @param reviews user reviewed clubs
	 */
	public UserPOJO(String id, String user, String pass, String email, String name, LocalDate bday) {
		super(id);
		this.username = user;
		this.password = pass;
		this.email = email;
		this.name = name;
		this.birthday = bday;

		reviewedClubs = new HashSet<String>();
	}
	
	/**
	 * User class whole constructor (for testing).
	 * @param id user id
	 * @param pass user password
	 * @param email user email
	 * @param name user name
	 * @param bday user birthday date
	 * @param rates user rated clubs
	 * @param reviews user reviewed clubs
	 */
	public UserPOJO(String id, String user, String pass, String email, String name, LocalDate bday, Set<String> reviews) {
		super(id);
		this.username = user;
		this.password = pass;
		this.email = email;
		this.name = name;
		this.birthday = bday;
		this.reviewedClubs = new HashSet<String>(reviews);
	}

	/**
	 * Returns the username.
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 * 
	 * @param newUsername
	 */
	public void setUsername(String user) {
		username = user;
	}
	
	/**
	 * Returns the password.
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password.
	 * @param password password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Returns the email.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email.
	 * @param email email
	 */
	public void setEmail(String email){
		this.email = email;		
	}
	
	/**
	 * Returns the name.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the birthday
	 * @return birthday
	 */
	public LocalDate getBirthday() {
		return birthday;
	}
	
	/**
	 * Sets the birthday
	 * @param birthday
	 */
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	/**
	 * Returns a Collection of reviewed clubs IDs.
	 * @see ProfileManagerSAImp.removeUser(String) 
	 * @return Collection of String
	 */
	public Collection<String> getReviewedClubs() {
		return (Collection<String>) reviewedClubs;
	}

	/**
	 * Adds a club to the reviewed clubs set.
	 * 
	 * @param clubID reviewed club id
	 */
	public void addReviewed(String clubID) {
		reviewedClubs.add(clubID);
	}

	/**
	 * Removes a club from the reviewed clubs set.
	 * 
	 * @param clubID unreviewed club id
	 */
	public void removeReviewed(String clubID) {
		reviewedClubs.remove(clubID);
	}
}
