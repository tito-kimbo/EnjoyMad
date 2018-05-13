package es.ucm.fdi.business.searchengine.filters;

import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.business.data.FilterPOJO;

/**
 * General scheme to build new <code>Filters</code>. Clone is implemented
 * for prototyping purposes.
 */
public interface FilterStrategy extends Cloneable{
	
	/**
	 * Clones the filter with the relevant parameters.
	 * 
	 * @param fp	Object containing the information required to build the filter.
	 * @return		The cloned object.
	 */
	public Object clone(FilterPOJO fp);
	
	/**
	 * Determines whether a specific <code>Club</code> matches certain criteria (depending
	 * on the specific filter type).
	 * 
	 * @param c		<code>Club</code> to check.
	 * @return		Whether the <code>Club</code> meets the criteria.
	 */
	abstract boolean filter(ClubPOJO c);
}
