package es.ucm.fdi.bussines.SearchEngine;

import es.ucm.fdi.business.SearchEngine.Filters.LocationFilter;
import es.ucm.fdi.business.SearchEngine.Filters.LocationFilter.JsonReader;
import es.ucm.fdi.integration.data.ClubPOJO;
import java.io.IOException;
import java.util.HashSet;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/** @author Francisco Javier Blázquez Martínez.*/
public class LocationFilterTest
{
	//@Test
	public void getJsonObjectFromUrl()
	{
		/*This method checks that the JSON reading from url process is exception free for random chosen coordinates.*/
		String requestURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" +
							+ 47.948706    + "," + -5.544259   		   + "&destinations=" +
							+ 20.9507191   + "," + -3.5397826999999324 + "&key=" + LocationFilter.API_KEY;

		try	
		{
			JSONObject json = JsonReader.readJsonFromUrl(requestURL);
		}catch(Exception e) 
		{
			assertTrue(false);
		}
	}
	
	@Test
	public void getDistanceFrom2GpsPoints()
	{
		/*This method calls to getNavigableDistance() to test if it works properly, this also checks
		 * the JSON reading from URL process.
		 * 
		 * That two gps coordinates are just 1.8km away. The final assert should be true.
		 * */
		
		LocationFilter filter = new LocationFilter("5", "38.948706", "-2.544259");
		ClubPOJO club = new ClubPOJO("id","name", "calle", 0, new HashSet<String>());
		
		club.setLatitude(38.9507191);
		club.setLongitude(-2.5397826999999324);
		
		boolean inRange = filter.filter(club);
		
		assertTrue(inRange);
	}
}
