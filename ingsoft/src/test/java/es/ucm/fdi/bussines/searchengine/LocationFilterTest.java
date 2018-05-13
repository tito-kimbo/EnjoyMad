package es.ucm.fdi.bussines.searchengine;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.searchengine.FilterMapper;
import es.ucm.fdi.business.searchengine.filters.LocationFilterBO;
import es.ucm.fdi.business.searchengine.filters.LocationFilterBO.JsonReader;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.Location;

import java.util.HashSet;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LocationFilterTest
{

	/**	This method checks that the JSON reading from url process is exception free for random
	 *  chosen coordinates.	*/
	//@Test
	public void getJsonObjectFromUrl()
	{
		String requestURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" +
							+ 47.948706    + "," + -5.544259   		   + "&destinations=" +
							+ 20.9507191   + "," + -3.5397826999999324 + "&key=" + LocationFilterBO.API_KEY;

		try	
		{
			JsonReader.readJsonFromUrl(requestURL);
		}catch(Exception e) 
		{
			fail("Exception when reading valid JSON from " + requestURL);
		}
	}
	
	/**This method calls to getNavigableDistance() to test if it works properly, this also checks
	 * the JSON reading from URL process.
	 * 
	 * That two GPS coordinates are just 1.8km away. The final assert should be true.
	 */
	@Test
	public void getDistanceFrom2GpsPoints()
	{
		FilterMapper.addAll();
		ClubPOJO club = new ClubPOJO("id","name", "calle", 0, new Location(0,0), 0);
		LocationFilterBO filter = new LocationFilterBO("5", "38.948706", "-2.544259");
		
		club.setLatitude(38.9507191);
		club.setLongitude(-2.5397826999999324);
		
		boolean inRange = filter.filter(club);
		
		assertTrue("Error filtering by distance: club should be in range.", inRange);
	}
}
