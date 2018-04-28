package es.ucm.fdi.business.searchengine.filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

public class RatingFilterBO implements FilterBO{
	private float rating;
	
	public RatingFilterBO(){}
	
	public RatingFilterBO(float rating) {
		this.rating = rating;
	}

	public Object clone(FilterPOJO fp){
		return new RatingFilterBO(Float.parseFloat(fp.getParams().get(0)));
	}
	
	public boolean filter(ClubPOJO c) {
		return c.getRating() >= rating;
	}

}
