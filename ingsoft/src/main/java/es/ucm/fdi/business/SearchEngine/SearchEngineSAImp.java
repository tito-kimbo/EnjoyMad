package es.ucm.fdi.business.SearchEngine;

import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.data.ClubPOJO;

import es.ucm.fdi.business.util.Element;
import es.ucm.fdi.business.data.FilterPOJO;

import es.ucm.fdi.business.SearchEngine.FilterMapper;
import es.ucm.fdi.business.SearchEngine.Filters.Filter;

public class SearchEngineSAImp implements SearchEngineSA {
	private List<Element<ClubPOJO>> searchResults;
	private ClubDAO clubAccess;
	
	/**
	 * Constructor for the <code>SearchEngineSAImp</code>. Requires to provide a valid 
	 * <code>ClubDAO</code>
	 * 
	 * @param clubs	DAO implementation to access to the club data
	 */
	public SearchEngineSAImp(ClubDAO clubs){
		clubAccess = clubs;
	}
	
	/**
	 * @inheritDoc
	 */
	public void search(String words, List<FilterPOJO> filters){
		Element<ClubPOJO> aux;
		List<ClubPOJO> clubs;
		
		clubs = clubAccess.getClubs();
		searchResults = new ArrayList<Element<ClubPOJO>>();
		//Here we must find all the matching Data
		for(ClubPOJO c : clubs){
			aux = new Element<ClubPOJO>(c);
			aux.setVisible( c.getCommercialName().toLowerCase().contains(words.toLowerCase()) );
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
	
	/**
	 * @inheritDoc
	 */
	public ClubPOJO select(String id){
		return clubAccess.getClub(id);
	}
}
