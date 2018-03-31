package es.ucm.fdi.integration.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents a club.
 * @author Fco Borja
 */
public class ClubPOJO extends DataPOJO {
	String address;
	//Location coordinates;
	float price;
	
	/**
	 * Set of descriptive tags about the club. It will be initialized as
	 * a HashSet, which ensures constant time basic operations
	 * (add, remove, contains).
	 */
	Set<String> tags;
	
	/**
	 * Map relating users' IDs with their rate (1 to 10) on this club.
	 * It will be initialized as a HashMap.
	 */
	Map<String, Integer> userRates;
	float rating;

	/**
	 * Map relating users' IDs with their opinion about this club.
	 * It will be initialized as a HashMap.
	 */
	Map<String, String> userOpinions;
	
	/**
	 * Club class normal constructor
	 * @param id identification
	 * @param address address string
	 * @param price price
	 * @param tags set of tags
	 */
	public ClubPOJO(String id, String address, float price, Set<String> tags) {
		super(id);
		this.address = address;
		this.price = price;
		this.tags = new HashSet<String>(tags);

		userRates = new HashMap<String, Integer>();
		rating = 0.0f;
		userOpinions = new HashMap<String, String>();
	}

	/**
	 * Club class whole constructor (for testing)
	 * @param id identification
	 * @param address address string
	 * @param price price
	 * @param tags set of tags
	 * @param rates map of user->rates
	 * @param rating total rating
	 */
	public ClubPOJO(String id, String address, float price, Set<String> tags, Map<String, Integer> rates, float rating, Map<String, String> opinions){
		super(id);
		this.address = address;
		//this.coordinates = new Location(latitude,longitude);
		this.price = price;
		this.tags = new HashSet<String>(tags); // Set constructor
		this.userRates = new HashMap<String, Integer>(rates); // Map constructor
		this.rating = rating;	
		this.userOpinions = opinions;	
	}

	/**
	 * Returns the address string.
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Sets the string of the location.
	 * @param address new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Returns the price.
	 * @return price
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * Sets the price of a ticket.
	 * @return price of a ticket
	 */
	public void setPrice(float price) {
		if(price > 0)
			this.price = price;
	}

	/**
	 * Returns the tags.
	 * @return tags
	 */
	public Set<String> getTags() {
		return tags;
	}
	
	/**
	 * Sets the tags
	 * @param tags new tags
	 */
	public void setTags(Set<String> tags) {
		this.tags = new HashSet<String>(tags);
	}

	/**
	 * Adds a tag to the club tags set. If it already exists, it
	 * won't be duplicated.
	 * @param tag tag to be added
	 */
	public void addTag(String tag) {
		tags.add(tag);
	}

	/**
	 * Removes tag from the club tags set. If it is not
	 * in the set, nothing happens.
	 * @param tag tag to be removed
	 */
	public void removeTag(String tag) {
		tags.remove(tag);
	}

	/**
	 * Removes all club's tags.
	 */
	public void clearTags() {
		tags.clear();
	}

	/**
	 * Adds a new/modified user rate to the rates map
	 * and updates the total rating.
	 * @param userID rating user
	 * @param rate rate integer
	 */
	public void addUserRate(String userID, int rate) {
		int numRates = userRates.size();
		
		if (userRates.containsKey(userID)) {
			int previousRate = userRates.get(userID);

			rating += ( (rate - previousRate) / numRates );
		} else {
			rating = ( rating * ( numRates / (numRates + 1) ) ) + ( rate / (numRates + 1) );
		}

		userRates.put(userID, rate); // Overwrites previous rate (if exist)
	}

	/**
	 * Removes a user rate from the rates map (if present) 
	 * and updates the total rating.
	 * @param userID unrating user
	 */
	public void removeUserRate(String userID) {
		Integer removedRateInt = userRates.remove(userID);
		
		if (removedRateInt != null) {
			int removedRate = removedRateInt; // unbox
			int size = userRates.remove(userID);

			rating -= removedRate / (size + 1);
			rating *= (size + 1) / size;
		}		
	}

	/**
	 * Checks if total rating is correct (linear over user rates)
	 * @return if total rating is correct
	 */
	public boolean checkRating() {
		float checkedRating = 0.0f;
		int numRates = userRates.size();

		for(int r : userRates.values()) {
			checkedRating += (r / numRates);
		}

		if (checkedRating == rating) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a Collection with raters (users) IDs.
	 * @return Collection of String
	 */
	public Collection<String> getRaters() {
		return (Collection<String>) userRates.keySet();
	}
	
    /**
	 * Returns the rating.
	 * @return rating
	 */
	public float getRating() {
		return rating;
	}
	
	/**
	 * Sets the club's rating.
	 * @param rating new rating
	 */
	public void setRating(float rating) {
		this.rating = rating;
	}

	/**
	 * Adds a new/modified user opinion to the opinion map.
	 * @param userID reviewing user
	 * @param opinion user's opinion
	 */
	public void addUserOpinion(String userID, String opinion) {
		userOpinions.put(userID, opinion); // Overwrites previous rate (if exist)
	}

	/**
	 * Removes a user opinion (if present).
	 * @param userID unreviewing user
	 */
	public void removeUserOpinion(String userID) {
		userOpinions.remove(userID);
	}

	/**
	 * Returns a Collection with reviewers (users) IDs.
	 * @return Collection of String
	 */
	public Collection<String> getReviewers() {
		return (Collection<String>) userOpinions.keySet();
	}

	
	
	
	
	
	
	
	/**
	 * Returns the latitude.
	 * @return the latitude.
	 */
	/*public double getLatitude() {
		return this.coordinates.getLat();
	}*/
	/**
	 * Returns the longitude.
	 * @return the longitude.
	 */
	/*public double getLongitude() {
		return this.coordinates.getLng();
	}*/
}
