package es.ucm.fdi.business.searchengine.filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * Class able to determine whether a <code>Club</code> has a sufficiently large
 * rating.
 * 
 * @version 22.04.2018
 */
public class RatingFilterBO implements FilterBO {
	private float rating;

	/**
	 * Constructor for the class.
	 */
	public RatingFilterBO() {
	}

	/**
	 * Constructor for the class.
	 * 
	 * @param rating
	 *            Minimum rating to consider.
	 */
	public RatingFilterBO(float rating) {
		this.rating = rating;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object clone(FilterPOJO fp) {
		return new RatingFilterBO(Float.parseFloat(fp.getParams().get(0)));
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean filter(ClubPOJO c) {
		return c.getRating() >= rating;
	}

}
