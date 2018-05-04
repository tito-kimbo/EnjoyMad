package es.ucm.fdi.business.searchengine;

import java.util.List;

import es.ucm.fdi.business.util.ElementBO;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 *	This interface indicates the main functionality of the search engine. It also declares
 *	useful utility classes such as the generic <code>Element</code>
 */
public interface SearchEngineSA {
	
	/**
	 * Searches for clubs applying the given filters.
	 * 
	 * @param words		words of the search in progress.
	 * @param filters	list of filters to apply.
	 * 
	 * @return List of <code>ElementBO<ClubPOJO></code>
	 */
	public List<ElementBO<ClubPOJO>> search(String words, List<FilterPOJO> filters, UserPOJO usr);
	

}
