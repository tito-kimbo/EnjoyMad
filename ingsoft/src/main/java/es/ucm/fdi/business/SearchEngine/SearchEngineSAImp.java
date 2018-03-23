package es.ucm.fdi.business.SearchEngine;

import java.util.List;

import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.business.SearchEngine.FilterMapper;
import es.ucm.fdi.business.SearchEngine.Filters.Filter;

public class SearchEngineSAImp implements SearchEngineSA {
	private static List<Element<ClubPOJO>> searchResults;
	
	public void search(List<String> words, List<FilterPOJO> filters){
		ClubDAO clubAccess;
		List<ClubPOJO> clubs;
			
		//Here we must find all the matching Data
		//WHERE?
		
		for(FilterPOJO f : filters){
			Filter currentFilter = FilterMapper.mapFilter(f);
			for(Element<ClubPOJO> c : searchResults){
				c.setVisible(currentFilter.filter(c.getElement()));
			}
		}
	}
	
	public List<Element<ClubPOJO>> getSearchResults(){
		return searchResults;
	}
}
