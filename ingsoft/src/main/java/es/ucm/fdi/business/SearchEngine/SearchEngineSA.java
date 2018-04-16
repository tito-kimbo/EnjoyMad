package es.ucm.fdi.business.SearchEngine;

import java.util.List;

import es.ucm.fdi.business.util.ElementBO;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

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
	 */
	public void search(String words, List<FilterPOJO> filters);
	
	/**
	 *	Retrieves the data of the selected club.
	 */
	public ClubPOJO select(String id);
	
	/**
	 * Returns the results of a search as a list of <code>Element</code> of 
	 * type <code>ClubPOJO</code>.
	 */
	public List<ElementBO<ClubPOJO>> getSearchResults();

}
