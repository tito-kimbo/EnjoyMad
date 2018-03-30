package es.ucm.fdi.integration.data;

import java.util.List;
import java.util.ArrayList;

/**
 * Class that represents a club.
 * @author Fco Borja
 */
public class ClubPOJO extends DataPOJO {
	String location;
	//Location coordinates;
	float price, rating;
	List<String> tags;
	/**
	 * Club class constructor.
	 * @param id identification
	 * @param location location string
	 * @param latitude latitude
	 * @param longitud longitude
	 * @param price price
	 * @param tags tags
	 */
	public ClubPOJO(String id, String location, float price, List<String> tags){
		super(id);
		this.location = location;
		this.price = price;
		this.tags = tags;
		//this.coordinates = new Location(latitude,longitude);
	}
	/**
	 * Returns the rating.
	 * @return rating
	 */
	public float getRating() {
		return rating;
	}
	/**
	 * Returns the location string.
	 * @return location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * Sets the string of the location.
	 */
	public void setLocation(String location) {
		this.location = location;
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
		this.price = price;
	}
	/**
	 * Returns the tags.
	 * @return tags
	 */
	public List<String> getTags() {
		return tags;
	}
	/**
	 * Sets the tags
	 */
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
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
