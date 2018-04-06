package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import android.content.*;
import android.location.*;
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
import android.app.*;

/**
 * This class is responsible of deciding whether a Club is near enough to satisfy the client search filter or not.
 * 
 * @author Francisco Javier Blázquez Martínez
 */
public class LocationFilter implements Filter{
	
	private final static String API_KEY = "AIzaSyDCASxz1lerrq1zkYhhbO7FAKDrcmNx9xo";
	private final static double MILES_TO_KM = 1.60934;
	
	private double maxDistance;
	private double deviceLatitude;
	private double deviceLongitude;
	private Context myContext; 		//NEEDS INITIALIZATION, possibly used via Singleton pattern in external class
				
	
	public LocationFilter()	{
		//WARNING! Unimplemented, just to avoid error in FilterMapper
	}
	public LocationFilter(Context contexto)	{
		myContext = contexto;
	}
	public LocationFilter(String string, Context contexto)
	{
		maxDistance = Double.valueOf(string).doubleValue();
		myContext = contexto;
	}
	public boolean filter(ClubPOJO c) 
	{	
		//This function might need cleaning
		try {
			double distance = getNavigableDistance(c);	//Calculates distance
			return distance <= maxDistance;				//We check
		}catch(NullPointerException nptr) {//There is no location register
			
		}catch(MalformedURLException url) {//Bad GPS coordenates
			
		}catch(JSONException json) {//Parsing Json
			
		}catch(IOException io) {//Getting Json from url
			
		}catch(IllegalStateException state) {//There is no "distance" value in Json
			
		}catch(Exception e) {//This is unexpected!
		}
		
		return false;
	}

	public Object clone(FilterPOJO fp) {
		
		return new LocationFilter(fp.getParams().get(0), myContext);
	}
	
	
	/**
	 * Initializes {@link #deviceLatitude} and {@link #deviceLongitude} to the mobile device last known location.
	 * 
	 * @throws NullPointerException	In case the mobile phone has no location registers, so that it's not possible 
	 * to get the last and nearest location of the device.
	 */
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
	}
	
	/**
	 * Method to get the distance from the device to a club.
	 * 
	 * @param club Club to get distance to.
	 * @return  Distance from the device to the club.
	 * @throws MalformedURLException Really strange case related to invalid GPS coordinates or abusive use 
	 * of the API_KEY.
	 * @throws IOException If the request to the URL fails during the connection process.
	 * @throws JSONException If it's not possible to parse the JSON section from the URL.
	 */
	private double getNavigableDistance(ClubPOJO club) throws IOException, JSONException
	{
		getDeviceCoordinates();
		
		/* -> We have the device coordinates */
		
		String requestURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" +
							+ deviceLatitude     + "," + deviceLongitude     + "&destinations=" +
							+ club.getLatitude() + "," + club.getLongitude() + "&key=" + API_KEY;
		
		/*URL distanceRequest = new URL(requestURL);
		URLConnection connection = distanceRequest.openConnection();  
		connection.setDoOutput(true);  
		
		Scanner scanner = new Scanner(distanceRequest.openStream());
		String response = scanner.useDelimiter("\\Z").next();
		JSONObject json = Util.parseJson(response); 
		scanner.close();*/
		
		JSONObject json = JsonReader.readJsonFromUrl(requestURL);
		
		// ->We suppose loaded JSON correctly? /
		
		String aux = (String) json.get("rows");
		String[] values = aux.split("[,:{}\\[\\]\\s\\t\n]");
		
		for(int i = 0; i < 10000; i++)
		{
			if(values[i].equals("distance"))
			{
				//should be  -> { ... ,"distance", "test", "41,6", ...}
				return MILES_TO_KM * Double.valueOf(values[i+2]).doubleValue();
			}
		}
		
		throw new IllegalStateException("Unable to get the distance from the JSON object.");
	}
	
	private static class JsonReader {
		//Check code marked by !!
		  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  
		  
		  }

		  private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream(); /*!! Usa URL no URLConnection !!*/
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
		  }
	}
	/*
	 * PENDING:
	 * 
	 * -> LocationManager initialization (requires a Context).
	 * -> Check javadoc 
	 * -> Exceptions?
	 * -> Clean JSON code
	 * 
	 * OBSERVATIONS:
	 * 
	 * -> myContext needs initialization!!!.
	 *    (getCoordinatesDevice() and LocationManager lm).
	 * -> We suppose correct latitude/longitude in ClubPOJO.
	 * -> For modifications in request format we need to modify requestURL in
	 *    getNavigableDistance().
	 * 
	 */
	
}
