package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

public class PriceFilter implements Filter{
	private int maxPrice;

	public PriceFilter(){}
	public PriceFilter(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Object clone(FilterPOJO fp){
		return new PriceFilter(Integer.parseInt(fp.getParams().get(0)));
	}
	
	public boolean filter(ClubPOJO c) {
		return maxPrice >= c.getPrice();
	}

}
