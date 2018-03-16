package es.ucm.fdi.business.SearchEngine;

import java.util.List;

import es.ucm.fdi.business.SearchEngine.Filters.Filter;

/**
 * 
 *
 */
public interface SearchEngineSA {
	
	/**
	 * 
	 * @param words
	 * @param filters
	 */
	public void search(List<String> words, List<Filter> filters);

}
