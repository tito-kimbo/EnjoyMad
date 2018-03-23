package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

public class ValorationFilter implements Filter{
	private float rating;
	
	public ValorationFilter(){}
	
	public ValorationFilter(float rating) {
		this.rating = rating;
	}

	public Object clone(FilterPOJO fp){
		return new ValorationFilter(Integer.parseInt(fp.getParams().get(0)));
	}
	
	public boolean filter(ClubPOJO c) {
		return c.getRating() >= rating;
	}

}
