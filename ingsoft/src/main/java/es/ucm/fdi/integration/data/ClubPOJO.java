package es.ucm.fdi.integration.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import es.ucm.fdi.business.data.TagPOJO;

/**
 * Class that represents a club.
 * 
 * @version 22.04.2018
 */
public class ClubPOJO extends DataPOJO implements Serializable{
	/**
	 * Generated serial UID.
	 */
	private static final long serialVersionUID = 3099911602624644548L;
	String commercialName;
	String address;
	float price;

	private Location location;
	

	/**
	 * Set of descriptive tags about the club. It will be initialized as
	 * a HashSet, which ensures constant time basic operations
	 * (add, remove, contains).
	 */
	private Set<TagPOJO> tags;
	private float rating;

	/**
	 * Map relating users' IDs with their opinion about this club.
	 * It will be initialized as a HashMap.
	 */
	private Map<String, ReviewPOJO> userReviews;
	
	/**
	 * Club class normal constructor
	 * @param id identification
	 * @param address address string
	 * @param price price
	 * @param tags set of tags
	 */
	public ClubPOJO(String id, String name, String address, float price, Set<TagPOJO> tags) {
		super(id);
		this.commercialName = name;
		this.address = address;
		this.price = price;
		this.tags = new HashSet<TagPOJO>(tags);

		// Location calculation
		location = new Location(address);

		rating = 0.0F;
		userReviews = new HashMap<String, ReviewPOJO>();
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
	public ClubPOJO(String id, String name, String address, float price, Location location, Set<TagPOJO> tags, 
			Map<String, Integer> rates, float rating, Map<String, ReviewPOJO> opinions) {
		super(id);
		this.commercialName = name;
		this.address = address;
		this.location = location;
		this.price = price;
		this.tags = new HashSet<TagPOJO>(tags); // Set constructor
		this.rating = rating;	
		this.userReviews = opinions;	
	}
	
	
	/**
	 * Club class copy constructor
	 * @param otherClub club to be copied
	 */
	
	public ClubPOJO(ClubPOJO otherClub) {
		super(otherClub.getID());
		this.commercialName = otherClub.getCommercialName();
		this.address = otherClub.getAddress();
		this.price = otherClub.getPrice();
		this.tags = otherClub.getTags();

		// Location calculation
		location = new Location(otherClub.getLatitude(),otherClub.getLongitude());

		rating = otherClub.getRating();
		
		//FALTA COPIAR USERREVIEWS. LUEGO VEO COMO HACERLO. ME TENGO QUE IR.
		userReviews = new HashMap<String, ReviewPOJO>();
	}

	public ClubPOJO(String id, String commercialName, String address,  float price, Location location, float rating) {
		super(id);
		this.commercialName = commercialName;
		this.address = address;
		this.location = location;
		this.price = price;
		this.rating = rating;	
		this.tags = new HashSet<TagPOJO>();
		this.userReviews = new  HashMap<String, ReviewPOJO>();
	}

	/**
	 * Returns the club commercial name.
	 * @return club name
	 */
	public String getCommercialName() {
		return commercialName;
	}

	/**
	 * Sets the club commercial name.
	 * @param name new name
	 */
	public void setCommercialName(String name) {
		commercialName = name;
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
	 * @param price price of a ticket
	 */
	public void setPrice(float price) {
		if(price > 0)
			this.price = price;
	}

	/**
	 * Returns the location of the club.
	 * 
	 * @return <code>Location</code> of <code>Club</code>
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Sets the location of the club.
	 * 
	 * @param location new club location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Returns the latitude coordinate of the club.
	 * 
	 * @return <code>Double</code> with the club latitude
	 */
	public Double getLatitude() {
		return ( location.getLat() );
	}

	/**
	 * Sets the latitude coordinate of the club.
	 * 
	 * @param latitude <code>Double</code> with new latitude
	 */
	public void setLatitude(Double latitude) {
		location.setLat(latitude);
	}

	/**
	 * Returns the longitude coordinate of the club.
	 * 
	 * @return <code>Double</code> with the club longitude
	 */
	public Double getLongitude() {
		return ( location.getLng() );
	}

	/**
	 * Sets the longitude coordinate of the club.
	 * 
	 * @param longitude <code>Double</code> with new longitude
	 */
	public void setLongitude(Double longitude) {
		location.setLng(longitude);
	}

	/**
	 * Returns the tags.
	 * @return tags
	 */
	public Set<TagPOJO> getTags() {
		return tags;
	}
	
	/**
	 * Sets the tags
	 * @param tags new tags
	 */
	public void setTags(Set<TagPOJO> tags) {
		this.tags = new HashSet<TagPOJO>(tags);
	}

	/**
	 * Adds a tag to the club tags set. If it already exists, it
	 * won't be duplicated.
	 * @param tag tag to be added
	 */
	public void addTag(TagPOJO tag) {
		tags.add(tag);
	}

	/**
	 * Removes tag from the club tags set. If it is not
	 * in the set, nothing happens.
	 * @param tag tag to be removed
	 */
	public void removeTag(TagPOJO tag) {
		tags.remove(tag);
	}

	/**
	 * Removes all club's tags.
	 */
	public void clearTags() {
		tags.clear();
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
	 * Adds a new/modified user review to the review map.
	 * @param userID reviewing user
	 * @param review user's review
	 */
	public void addUserReview(String userID, ReviewPOJO review) {
		userReviews.put(userID, review); // Overwrites previous rate (if exist)
		
		// Now updates the current rating
		
		/*
		 * FORMULA: R = ( avg * (n-1) + opRating ) / n = ( rating + opRating ) / n
		 */
		rating = (float) ( 
				( rating * (userReviews.size() - 1) + review.getRating() ) / 
				( (double) userReviews.size() ) 
				);
	}

	/**
	 * Removes a user review (if present).
	 * @param userID user removing review
	 */
	public void removeUserReview(String userID) {
		ReviewPOJO removedReview = userReviews.get(userID);

		// If there is no review, nothing is modified.
		if ( removedReview != null ) {
			// Review is removed
			userReviews.remove(userID);

			// Now updates the current rating		
			if (userReviews.size() == 0) {
				rating = 0;
			} else {
				rating = (float) (((userReviews.size() + 1) * rating - removedReview.getRating())
						/ (userReviews.size()));
			}
		}		
	}

	/**
	 * Returns a Collection with reviewers (Users) IDs.
	 * @return Collection of String
	 */
	public Collection<String> getReviewers() {
		return ( (Collection<String>) userReviews.keySet() );
	}

	public Map getReviews() {
		return userReviews;
	}
}
