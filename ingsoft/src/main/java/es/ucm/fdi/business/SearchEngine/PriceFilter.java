package es.ucm.fdi.business.SearchEngine;

import es.ucm.fdi.data.ClubPOJO;

public class PriceFilter extends Filter{
	private int maxPrice;
	@Override
	public boolean filter(ClubPOJO c) {
		return maxPrice >= c.getPrice();
	}

}
