package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

public class PriceFilterBO implements FilterBO{
	private float maxPrice;

	public PriceFilterBO(){}
	public PriceFilterBO(float maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Object clone(FilterPOJO fp){
		return new PriceFilterBO(Float.parseFloat(fp.getParams().get(0)));
	}
	
	public boolean filter(ClubPOJO c) {
		return maxPrice >= c.getPrice();
	}

}
