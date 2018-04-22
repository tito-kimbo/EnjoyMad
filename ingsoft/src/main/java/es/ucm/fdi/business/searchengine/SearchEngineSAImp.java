package es.ucm.fdi.business.searchengine;

import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.business.searchengine.FilterMapper;
import es.ucm.fdi.business.searchengine.filters.FilterBO;
import es.ucm.fdi.business.util.ElementBO;
import es.ucm.fdi.business.data.FilterPOJO;

public class SearchEngineSAImp implements SearchEngineSA {
	private List<ElementBO<ClubPOJO>> searchResults;
	private ClubDAO clubAccess;
	
	/**
	 * Constructor for the <code>SearchEngineSAImp</code>. Requires to provide a valid 
	 * <code>ClubDAO</code>
	 * 
	 * @param clubs	DAO implementation to access to the club data
	 */
	public SearchEngineSAImp(ClubDAO clubs){
		clubAccess = clubs;
		searchResults = new ArrayList<ElementBO<ClubPOJO>>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void search(String words, List<FilterPOJO> filters){
		ElementBO<ClubPOJO> aux;
		List<ClubPOJO> clubs;
		
		clubs = clubAccess.getClubs();
		
		//Here we must find all the matching Data
		for(ClubPOJO c : clubs){
			aux = new ElementBO<ClubPOJO>(c);
			aux.setVisible( c.getCommercialName().toLowerCase().contains(words.toLowerCase()) );
			searchResults.add(aux);
		}
		
		for(FilterPOJO f : filters){
			FilterBO currentFilter = FilterMapper.mapFilter(f);
			for(ElementBO<ClubPOJO> c : searchResults){
				c.setVisible(currentFilter.filter(c.getElement()));
			}
		}
	}
	
	/**
	 * Getter method for {@link #searchResults}
	 * 
	 * @return The results of the last search.
	 */
	public List<ElementBO<ClubPOJO>> getSearchResults(){
		return searchResults;
	}
}
