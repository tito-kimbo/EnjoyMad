package es.ucm.fdi.integration.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

/**
 * Class that contains representative information of a certain user.
 * 
 * @version 22.04.2018
 */
public class UserPOJO extends DataPOJO implements Serializable{
	String nickname, password, email, name; 
	LocalDate birthday;
	
	private List <ClubPOJO> preferencesList;
	private Map<String, Integer> valueTags;
	/**
	 * Set of rated clubs IDs.
	 */
	Set<String> ratedClubs;


	/**
	 * Set of reviewed clubs IDs.
	 */
	private Set<String> reviewedClubs;

	/**
	 * User class normal constructor
	 * @param id nickname id
         * @param nickname
	 * @param pass nickname password
	 * @param email nickname email
	 * @param name nickname name
	 * @param bday nickname birthday date
	 */
	public UserPOJO(String id, String nickname, String pass, String email, String name, LocalDate bday) {
		super(id);
		this.nickname = nickname;
		this.password = pass;
		this.email = email;
		this.name = name;
		this.birthday = bday;

		reviewedClubs = new HashSet<String>();
	}
	
	
	public List<ClubPOJO> getPreferencesList() {
		return preferencesList;
	}


	public void setPreferencesList(List<ClubPOJO> preferencesList) {
		this.preferencesList = preferencesList;
	}


	public Map<String, Integer> getValueTags() {
		return valueTags;
	}


	public void setValueTags(Map<String, Integer> valueTags) {
		this.valueTags = valueTags;
	}


	/**
	 * User class whole constructor (for testing).
	 * @param id nickname id
         * @param nickname
	 * @param pass nickname password
	 * @param email nickname email
	 * @param name nickname name
	 * @param bday nickname birthday date
	 * @param reviews nickname reviewed clubs
	 */
	public UserPOJO(String id, String nickname, String pass, String email, 
                String name, LocalDate bday, Set<String> reviews) {
		super(id);
		this.nickname = nickname;
		this.password = pass;
		this.email = email;
		this.name = name;
		this.birthday = bday;
		this.reviewedClubs = new HashSet<String>(reviews);
	}

	/**
	 * Returns the nickname.
	 * 
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Sets the nickname.
	 * 
         * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	 * Compares the <code>User</code> to another <code>User</code>.
	 * 
	 * @param user <code>User</code> to be compared to.
	 * @return whether or not the two <code>User</code> are the same.
	 */
	synchronized public boolean equals(UserPOJO user) {
		return (getID().equals(user.getID()) &&
				getName().equals(user.getName()) &&
				getBirthday().equals(user.getBirthday()) &&
				getEmail() == user.getEmail() &&
				getNickname().equals(user.getNickname()) &&
				getPassword() == user.getPassword() &&
				getPreferencesList() == user.getPreferencesList() &&
				CollectionUtils.isEqualCollection(getReviewedClubs(),user.getReviewedClubs()) &&
				getPreferencesList().equals(user.getPreferencesList()) &&
				getValueTags().equals(user.getValueTags()));
	}
}
