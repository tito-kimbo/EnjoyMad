package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

public class RatingFilter implements Filter{
	private float rating;
	
	public RatingFilter(){}
	
	public RatingFilter(float rating) {
		this.rating = rating;
	}

	public Object clone(FilterPOJO fp){
		return new RatingFilter(Float.parseFloat(fp.getParams().get(0)));
	}
	
	public boolean filter(ClubPOJO c) {
		return c.getRating() >= rating;
	}

}
