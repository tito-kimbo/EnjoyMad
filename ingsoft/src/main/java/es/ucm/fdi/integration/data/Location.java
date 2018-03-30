package es.ucm.fdi.integration.data;
/**
 * This class represents a location in coordinates.
 * @author Fco Borja
 * @author Carlijn
 */
public class Location {
	private double lat, lng;
	/**
	 * Class constructor.
	 * @param latitude latitude
	 * @param longitude longitude
	 */
	public Location(double latitude, double longitude) {
		this.lat = latitude;
		this.lng = longitude;
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
}