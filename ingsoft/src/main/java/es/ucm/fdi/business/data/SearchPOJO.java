package es.ucm.fdi.business.data;

import java.util.List;

import es.ucm.fdi.business.util.Element;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * Class that registers a search and its results.
 */
public class SearchPOJO {
	private String words;
	private List<Element<ClubPOJO>> searchResults;
	
	public SearchPOJO(String w){
		words = w;
		searchResults = null;
	}
	
	public SearchPOJO(String w, List<Element<ClubPOJO>> sr){
		words = w;
		searchResults = sr;
	}
	
	public String getWords(){
		return words;
	}
	
	public List<Element<ClubPOJO>> getSearchResults(){
		return searchResults;
	}
}
