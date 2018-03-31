package es.ucm.fdi.integration.data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class UserPOJO extends DataPOJO {
	String password, email, name;
	LocalDate birthday;

	/**
	 * Set of rated clubs IDs.
	 */
	Set<String> ratedClubs;

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
	public UserPOJO(String id, String pass, String email, String name, LocalDate bday) {
		super(id);
		setPassword(pass);
-		setEmail(email);
-		setName(name);
-		setBirthday(bday);

		ratedClubs = new HashSet<String>();
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
	public UserPOJO(String id, String pass, String email, String name, LocalDate bday, Set<String> rates, Set<String> reviews) {
		super(id);
		this.password = pass;
		this.email = email;
		this.name = name;
		this.birthday = bday;
		this.ratedClubs = new HashSet<String>(rates);
		this.reviewedClubs = new HashSet<String>(reviews);
	}
  
	/**
	 * Returns whether the email address is valid.
	 * @return valid
	 */
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
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
		if(isValidEmailAddress(email)){
			this.email = email;
		}
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
	 * Returns a Collection of rated clubs IDs.
	 * @see es.ucm.fdi.business.ProfileManagement.ProfileManagerSAImp#removeUser(String) removeUser
	 * @return Collection of String
	 */
	public Collection<String> getRatedClubs() {
		return (Collection<String>) ratedClubs;
	}

	/**
	 * Returns a Collection of reviewed clubs IDs.
	 * @see es.ucm.fdi.business.ProfileManagement.ProfileManagerSAImp#removeUser(String) removeUser
	 * @return Collection of String
	 */
	public Collection<String> getReviewedClubs() {
		return (Collection<String>) reviewedClubs;
	}

	/**
	 * Removes a club from the rated clubs set.
	 */
	public void removeRated(String clubID) {
		ratedClubs.remove(clubID);
	}

	/**
	 * Removes a club from the reviewed clubs set.
	 */
	public void removeReviewed(String clubID) {
		reviewedClubs.remove(clubID);
	}
}
