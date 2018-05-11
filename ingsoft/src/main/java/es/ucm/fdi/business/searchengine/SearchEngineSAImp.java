package es.ucm.fdi.business.searchengine;

import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;
import es.ucm.fdi.business.searchengine.FilterMapper;
import es.ucm.fdi.business.searchengine.filters.FilterBO;
import es.ucm.fdi.business.util.ElementHelper;
import es.ucm.fdi.business.data.FilterPOJO;

public class SearchEngineSAImp implements SearchEngineSA {
	
	/**
	 * {@inheritDoc}
	 */
	public synchronized List<ElementHelper<ClubPOJO>> search(String words, List<FilterPOJO> filters, UserPOJO usr){
		ElementHelper<ClubPOJO> aux;
		List<ElementHelper<ClubPOJO>> searchResults = new ArrayList<ElementHelper<ClubPOJO>>();
		List<ClubPOJO> clubs;
		clubs = usr.getPreferencesList();
		
		//Here we must find all the matching Data
		for(ClubPOJO c : clubs){
			aux = new ElementHelper<ClubPOJO>(c);
			aux.setVisible( c.getCommercialName().toLowerCase().contains(words.toLowerCase()) );
			searchResults.add(aux);
		}
		
		for(FilterPOJO f : filters){
			FilterBO currentFilter = FilterMapper.mapFilter(f);
			for(ElementHelper<ClubPOJO> c : searchResults){
				c.setVisible(currentFilter.filter(c.getElement()));
			}
		}
		return searchResults;
	}
	
}
