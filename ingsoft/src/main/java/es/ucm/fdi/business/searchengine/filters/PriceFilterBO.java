package es.ucm.fdi.business.searchengine.filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * Class able to determine whether a <code>Club</code> has sufficiently low ticket price.
 * 
 * @version 22.04.2018
 */
public class PriceFilterBO implements FilterBO{
	private float maxPrice;

	/**
	 * Empty constructor for the class.
	 */
	public PriceFilterBO(){}
	/**
	 * Constructor with price parameter.
	 * 
	 * @param maxPrice	Maximum price to consider.
	 */
	public PriceFilterBO(float maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Object clone(FilterPOJO fp){
		return new PriceFilterBO(Float.parseFloat(fp.getParams().get(0)));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean filter(ClubPOJO c) {
		return maxPrice >= c.getPrice();
	}

}
