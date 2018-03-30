package es.ucm.fdi.integration.data;

import java.util.List;
import java.util.ArrayList;

/**
 * Class that represents a club.
 * @author Fco Borja
 */
public class ClubPOJO extends DataPOJO {
	String address;
	//Location coordinates;
	float price, rating;
	List<String> tags;
	/**
	 * Club class constructor.
	 * @param id identification
	 * @param address address string
	 * @param latitude latitude
	 * @param longitud longitude
	 * @param price price
	 * @param tags tags
	 */
	public ClubPOJO(String id, String address, float price, ArrayList<String> tags){
		super(id);
		setAddress(address);
		setPrice(price);
		setTags(tags);
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
	 * Returns the address string.
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * Sets the string of the location.
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
