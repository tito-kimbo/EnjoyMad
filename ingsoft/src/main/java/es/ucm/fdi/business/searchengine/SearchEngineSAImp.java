package es.ucm.fdi.business.searchengine;

import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;
import es.ucm.fdi.business.searchengine.FilterMapper;
import es.ucm.fdi.business.searchengine.filters.FilterBO;
import es.ucm.fdi.business.util.ElementBO;
import es.ucm.fdi.business.data.FilterPOJO;

public class SearchEngineSAImp implements SearchEngineSA {
	private ClubDAO clubAccess;
	
	/**
	 * @param clubAccess is the DAO to access the clubs
	 */
	public SearchEngineSAImp(ClubDAO clubAccess){
		this.clubAccess = clubAccess;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public List<ElementBO<ClubPOJO>> search(String words, List<FilterPOJO> filters, UserPOJO usr){
		ElementBO<ClubPOJO> aux;
		List<ElementBO<ClubPOJO>> searchResults = new ArrayList<ElementBO<ClubPOJO>>();
		List<ClubPOJO> clubs;
		clubs = usr.getPreferencesList();
		
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
		return searchResults;
	}
	
}
