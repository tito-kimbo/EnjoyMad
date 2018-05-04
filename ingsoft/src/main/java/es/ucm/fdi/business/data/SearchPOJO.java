package es.ucm.fdi.business.data;

import java.util.List;

import es.ucm.fdi.business.util.ElementHelper;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * Class that registers a search and its results.
 * 
 * @version 22.04.2018
 */
public class SearchPOJO {
	private String words;
	private List<ElementHelper<ClubPOJO>> searchResults;
	
	public SearchPOJO(String w){
		words = w;
		searchResults = null;
	}
	
	public SearchPOJO(String w, List<ElementHelper<ClubPOJO>> sr){
		words = w;
		searchResults = sr;
	}
	
	public String getWords(){
		return words;
	}
	
	public List<ElementHelper<ClubPOJO>> getSearchResults(){
		return searchResults;
	}
}
