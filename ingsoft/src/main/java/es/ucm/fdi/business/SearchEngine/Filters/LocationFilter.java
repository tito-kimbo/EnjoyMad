package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

public class LocationFilter implements Filter{

	public boolean filter(ClubPOJO c) {
		return false;
	}

	public Object clone(FilterPOJO fp) {
		// TODO Auto-generated method stub
		return null;
	}

}
