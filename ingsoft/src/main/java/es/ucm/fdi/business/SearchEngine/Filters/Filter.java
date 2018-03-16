package es.ucm.fdi.business.SearchEngine.Filters;

import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * 
 *
 */
public abstract class Filter {

	boolean filter(ClubPOJO c){
		return false;
	}
}
