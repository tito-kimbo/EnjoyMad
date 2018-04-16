package es.ucm.fdi.integration.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import  es.ucm.fdi.integration.util.OpinionPOJO;

/**
 * Class that represents a club.
 * @author Fco Borja
 */
public class ClubPOJO extends DataPOJO {
	private String commercialName;
	private String address;
	private float price;
	
	private double latitude;
	private double longitude;
	
	
	/**
	 * Set of descriptive tags about the club. It will be initialized as
	 * a HashSet, which ensures constant time basic operations
	 * (add, remove, contains).
	 */
	private Set<String> tags;
	private float rating;

	/**
	 * Map relating users' IDs with their opinion about this club.
	 * It will be initialized as a HashMap.
	 */
	private Map<String, OpinionPOJO> userOpinions;
	
	/**
	 * Club class normal constructor
	 * @param id identification
	 * @param address address string
	 * @param price price
	 * @param tags set of tags
	 */
	public ClubPOJO(String id, String name, String address, float price, Set<String> tags) {
		super(id);
		this.commercialName = name;
		this.address = address;
		this.price = price;
		this.tags = new HashSet<String>(tags);

		rating = 0.0f;
		userOpinions = new HashMap<String, OpinionPOJO>();
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
	public ClubPOJO(String id, String name, String address, float price, Set<String> tags, float rating, Map<String, OpinionPOJO> opinions){
		super(id);
		this.commercialName = name;
		this.address = address;
		//this.coordinates = new Location(latitude,longitude);
		this.price = price;
		this.tags = new HashSet<String>(tags); // Set constructor
		this.rating = rating;	
		this.userOpinions = opinions;	
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
	public void addUserOpinion(String userID, OpinionPOJO opinion) {
		userOpinions.put(userID, opinion); // Overwrites previous rate (if exist)
		//Now updates the current rating
		
		/*
		 * FORMULA: R = (avg*(n-1)+opRating)/n = (rating+opRating)/n
		 */
		rating = (float)((rating*(userOpinions.size()-1)+opinion.getRating())
				/((double)userOpinions.size()));
	}

	/**
	 * Removes a user opinion (if present).
	 * @param userID unreviewing user
	 */
	public void removeUserOpinion(String userID) {
		OpinionPOJO aux = userOpinions.get(userID);
		userOpinions.remove(userID);
		//Now updates the current rating
		
		if(userOpinions.size() == 0){
			rating = 0;
		}else{
			rating = (float )( (((userOpinions.size()+1)*rating)-aux.getRating())
					/userOpinions.size() );
		}
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
	public double getLatitude() {
		return latitude;
	}
	/**
	 * Returns the longitude.
	 * @return the longitude.
	 */
	public double getLongitude() {
		return longitude;
	}

	public void setLatitude(double lat)
	{
		latitude = lat;		
	}
	public void setLongitude(double lon)
	{
		longitude = lon;
	}
}