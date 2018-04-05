package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import android.content.Context;
import android.location.*;
import org.json.simple.parser.*;


public class LocationFilter implements Filter{

	public boolean filter(ClubPOJO c) {	
		
		
		return false;
	}

	public Object clone(FilterPOJO fp) {
		
		return null;
	}
	
	//EN DESARROLLO!
	public void getCoordinates()
	{
		//NEEDS A CONTEXT -> Create a main Singleton base for the App with its context
		
		//LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		//Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		//double longitude = location.getLongitude();
		//double latitude = location.getLatitude();
	}

}
