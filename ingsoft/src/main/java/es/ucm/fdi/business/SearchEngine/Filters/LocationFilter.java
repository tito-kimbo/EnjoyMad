package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import android.location.*;
import org.json.simple.parser.*;


public class LocationFilter implements Filter{

	public boolean filter(ClubPOJO c) {	
		
		
		return false;
	}

	public Object clone(FilterPOJO fp) {
		
		return null;
	}
	public GeoPoint getLocationFromAddress(String strAddress){

		Geocoder coder = new Geocoder(this);
		List<Address> address;
		GeoPoint p1 = null;

		try {
		    address = coder.getFromLocationName(strAddress,5);
		    if (address==null) {
		       return null;
		    }
		    Address location=address.get(0);
		    location.getLatitude();
		    location.getLongitude();

		    p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
		                      (double) (location.getLongitude() * 1E6));

		    return p1;
		    }
		}

}
