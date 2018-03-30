package es.ucm.fdi.business.SearchEngine;

import java.util.List;
import java.util.ArrayList;


import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;

import es.ucm.fdi.business.util.Element;
import es.ucm.fdi.business.data.FilterPOJO;

import es.ucm.fdi.business.SearchEngine.FilterMapper;
import es.ucm.fdi.business.SearchEngine.Filters.Filter;

public class SearchEngineSAImp implements SearchEngineSA {
	private static List<Element<ClubPOJO>> searchResults;
	
	public void search(String words, List<FilterPOJO> filters){
		ClubDAO clubAccess = new ClubDAOImp();
		Element<ClubPOJO> aux;
		List<ClubPOJO> clubs;
		
		clubs = clubAccess.getClubs();
		searchResults = new ArrayList<Element<ClubPOJO>>();
		//Here we must find all the matching Data
		for(ClubPOJO c : clubs){
			aux = new Element<ClubPOJO>(c);
			aux.setVisible( c.getID().toLowerCase().contains(words.toLowerCase()) );
			searchResults.add(aux);
		}
		
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
	
	public void select(){
		
	}
}
