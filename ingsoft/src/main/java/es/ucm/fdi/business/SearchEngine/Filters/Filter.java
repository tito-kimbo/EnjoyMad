package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * 
 *
 */
public interface Filter {

	abstract boolean filter(ClubPOJO c);
}
