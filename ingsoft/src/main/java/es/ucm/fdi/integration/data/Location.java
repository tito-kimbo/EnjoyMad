package es.ucm.fdi.integration.data;
/**
 * This class represents a location in coordinates.
 * 
 * @version 22.04.2018
 */
public final class Location {
	private double lat, lng;
	
	/**
	 * Class constructor.
	 * @param latitude latitude
	 * @param longitude longitude
	 */
	public Location(double latitude, double longitude) {
		setLat(latitude);
		setLng(longitude);
	}

	/**
	 * Constructor of <code>Location</code> from an <code>Address</code>.
	 * 
	 * @param address <code>String</code> with <code>Address</code>
	 */
	public Location(String address) {
		// Implement coordinates calculation with API
		setLat(40.452926F);
		setLng(-3.733293F);
	}
        
	/**
	 * Returns the latitude.
	 * @return latitude
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * Sets the latitude.
	 * @param lat latitude
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
	/**
	 * Returns the longitude.
	 * @return longitude
	 */
	public double getLng() {
		return lng;
	}
	/**
	 * Sets the longitude.
	 * @param lng longitude.
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public boolean equals(Location loc) {
		return(getLat() == loc.getLat() &&
		getLng() == loc.getLng());

	}

}