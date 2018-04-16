package es.ucm.fdi.business.data;

import java.util.List;

import es.ucm.fdi.business.util.ElementBO;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * Class that registers a search and its results.
 */
public class SearchPOJO {
	private String words;
	private List<ElementBO<ClubPOJO>> searchResults;
	
	public SearchPOJO(String w){
		words = w;
		searchResults = null;
	}
	
	public SearchPOJO(String w, List<ElementBO<ClubPOJO>> sr){
		words = w;
		searchResults = sr;
	}
	
	public String getWords(){
		return words;
	}
	
	public List<ElementBO<ClubPOJO>> getSearchResults(){
		return searchResults;
	}
}
