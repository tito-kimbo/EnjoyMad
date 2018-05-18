package es.ucm.fdi.integration.data;

import java.io.IOException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.code.geocoder.Geocoder;

import es.ucm.fdi.business.searchengine.filters.LocationFilterStrategy.JsonReader;

/**
 * This class represents a location in coordinates.
 * 
 * @version 22.04.2018
 */
public class Location {
	
	private final static String API_KEY = "AIzaSyC8G4EpP1xEPSWWf-A_7yUUyrni3oYj7X0";
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
		try{
			String requestURL = "https://maps.googleapis.com/maps/api/geocode/json?address="
							+ URLEncoder.encode(address, "UTF-8")
							+ "&key="+API_KEY;
			JSONObject json = JsonReader.readJsonFromUrl(requestURL);
			JSONArray results = json.getJSONArray("results");
			
			JSONObject obj1 = results.getJSONObject(0);
			JSONObject geom = obj1.getJSONObject("geometry");
			JSONObject location = geom.getJSONObject("location");
			
			Object lat = location.get("lat");
			Object lng = location.get("lng");
			setLat((Double)lat);
			setLng((Double)lng);
		}catch(IOException ioe){
			//System.out.println("IOEXCEPTION " + ioe.getMessage());
		}catch(JSONException jse){
			//System.out.println("JSONEXCEPTION" + jse.getMessage());
		}
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {

		if (! (o instanceof Location)) {
			return false;
		}

		Location locToCompare = (Location) o;

		return 	lat == locToCompare.lat
					&& lng == locToCompare.lng;
	}
}