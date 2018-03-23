package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.business.data.FilterPOJO;

/**
 * 
 *
 */
public interface Filter extends Cloneable{
	public Object clone(FilterPOJO fp);
	abstract boolean filter(ClubPOJO c);
}
