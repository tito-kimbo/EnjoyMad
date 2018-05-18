package es.ucm.fdi.business.searchengine.filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * This class is responsible of deciding whether a Club is near enough to
 * satisfy the client search filter or not.
 * 
 * @version 22.04.2018
 */
public class LocationFilterBO implements FilterBO {

	public final static String API_KEY = "AIzaSyDCASxz1lerrq1zkYhhbO7FAKDrcmNx9xo";
	private final static double MILES_TO_KM = 1.60934;

	private double maxDistance;
	private double deviceLatitude;
	private double deviceLongitude;

	public LocationFilterBO() {
		// WARNING! Unimplemented, just to avoid error in FilterMapper
	}

	/*--------------------- THIS CALL IS SUPOSED TO BE DONE BEFORE GETTING HERE ------------------------
	 * Initializes {@link #deviceLatitude} and {@link #deviceLongitude} to the mobile device last known location.
	 * 
	 * @throws NullPointerException	In case the mobile phone has no location registers, so that it's not possible 
	 * to get the last and nearest location of the device.
	 *
	private void getDeviceCoordinates() //IN DEVELOPMENT!
	{
		//NEEDS A CONTEXT -> Create a main Singleton base for the App with its context (?)
		
		String context = Context.LOCATION_SERVICE;
		LocationManager lm = (LocationManager)myContext.getSystemService(context); 
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); 
	
		if(location == null)
			throw new NullPointerException("There is no last known location in this device.");
		
		deviceLongitude = location.getLongitude();
		deviceLatitude  = location.getLatitude();
		
		//We suppose correct latitude/longitude.
	}*/
	/**
	 * Creates a LocationFilter given the maximum distance selected an the GPS
	 * coordinates of the person.
	 * 
	 * @param maxDist
	 *            Maximum distance the person wants to travel to go party.
	 * @param devLati
	 *            GPS latitude coordinates.
	 * @param devLong
	 *            GPS longitude coordinates.
	 */
	public LocationFilterBO(String maxDist, String devLati, String devLong) {
		maxDistance = Double.valueOf(maxDist).doubleValue();
		deviceLatitude = Double.valueOf(devLati).doubleValue();
		deviceLongitude = Double.valueOf(devLong).doubleValue();
	}

	/**
	 * Decides whether a club is near enough to the person or not.
	 * 
	 * @param club
	 *            The club we are considering to filter.
	 * @return True if the distance to the club is smaller than
	 *         {@link maxDistance}
	 */
	public boolean filter(ClubPOJO club) {
		try {
			double distance = getNavigableDistance(club); // Calculates distance
			return distance <= maxDistance; // We check
		} catch (NullPointerException nptr) { // There is no location register

		} catch (MalformedURLException url) { // Bad GPS coordenates

		} catch (JSONException json) { // Parsing Json

		} catch (IOException io) { // Getting Json from url

		} catch (IllegalStateException state) { // There is no "distance" value
												// in Json

		} catch (Exception e) { // This is unexpected!

		}

		return false;
	}

	/**
	 * This method presuppose that the Filter has three attributes. Maximum
	 * distance to travel, person latitude and person longitude in this order.
	 * 
	 * @param fp
	 *            Filter object with the information the user gave us.
	 * @return A new LocationFilter with the information of the user.
	 */
	public Object clone(FilterPOJO fp) {

		return new LocationFilterBO(fp.getParams().get(0), fp.getParams()
				.get(1), fp.getParams().get(2));
	}

	/**
	 * Method to get the distance from the device to a club.
	 * 
	 * @param club
	 *            Club to get distance to.
	 * @return Distance from the device to the club.
	 * @throws MalformedURLException
	 *             Really strange case related to invalid GPS coordinates or
	 *             abusive use of the API_KEY.
	 * @throws IOException
	 *             If the request to the URL fails during the connection
	 *             process.
	 * @throws JSONException
	 *             If it's not possible to parse the JSON section from the URL.
	 */
	private double getNavigableDistance(ClubPOJO club) throws IOException,
			JSONException {
		/* -> We have the device coordinates an the club coordinates */

		String requestURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="
				+ +deviceLatitude
				+ ","
				+ deviceLongitude
				+ "&destinations="
				+ +club.getLatitude()
				+ ","
				+ club.getLongitude()
				+ "&key="
				+ API_KEY;

		JSONObject json = JsonReader.readJsonFromUrl(requestURL);

		/* -> We have the json object */

		JSONArray array = json.getJSONArray("rows");
		JSONObject object = array.getJSONObject(0);
		JSONArray array2 = object.getJSONArray("elements");
		JSONObject object2 = array2.getJSONObject(0);
		JSONObject object3 = object2.getJSONObject("distance");

		/* -> We have the element we wanted */

		String aux = (String) object3.get("text"); // "1,1 mi"
		aux = aux.substring(0, aux.length() - 3); // "1,1"
		aux.replaceAll(",", "."); // "1.1"

		return MILES_TO_KM * Double.valueOf(aux).doubleValue();

		// throw new
		// IllegalStateException("Unable to get the distance from the JSON object.");
	}

	/**
	 * Class just for reading a {@link JSONObject} from an url. Free source code
	 * published by Roland Illig.
	 * 
	 * @author Roland Illig
	 * @see https://stackoverflow.com/users/225757/roland-illig
	 */
	public static class JsonReader {
		// Check code marked by !!
		private static String readAll(Reader rd) throws IOException {
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			return sb.toString();

		}

		public static JSONObject readJsonFromUrl(String url)
				throws IOException, JSONException {
			InputStream is = new URL(url).openStream(); /*
														 * !! Usa URL no
														 * URLConnection !!
														 */
			try {
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						is, Charset.forName("UTF-8")));
				String jsonText = readAll(rd);
				JSONObject json = new JSONObject(jsonText);
				return json;
			} finally {
				is.close();
			}
		}
	}

	/*
	 * PENDING: -> Exceptions in getNavigableDistance?
	 * 
	 * OBSERVATIONS: -> We suppose correct latitude/longitude in ClubPOJO. But
	 * there is no initialization. -> We can get the distance between two gps
	 * coordinates of between two addresses. We have to decide which one we are
	 * going to use definitely.
	 */

}
