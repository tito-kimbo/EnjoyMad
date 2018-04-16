package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.business.data.FilterPOJO;

/**
 * General scheme to build new <code>Filters</code>. Clone is implemented
 * for prototyping purposes.
 */
public interface FilterBO extends Cloneable{
	public Object clone(FilterPOJO fp);
	abstract boolean filter(ClubPOJO c);
}
