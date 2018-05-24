package es.ucm.fdi.business.searchengine;

import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;
import es.ucm.fdi.business.searchengine.FilterMapper;
import es.ucm.fdi.business.searchengine.filters.FilterStrategy;
import es.ucm.fdi.business.util.ElementHelper;
import es.ucm.fdi.business.data.FilterPOJO;

/**
 * Implementation of the <code>SearchEngineSA</code> interface.
 */
public class SearchEngineSAImp implements SearchEngineSA {
	
	/**
	 * @param clubAccess is the DAO to access the clubs
	 */
	public SearchEngineSAImp() {}

	/**
	 * {@inheritDoc}
	 */
	public synchronized List<ElementHelper<ClubPOJO>> search(String words, List<FilterPOJO> filters, UserPOJO usr) {
		
		//To store the search result
		List<ElementHelper<ClubPOJO>> searchResults = new ArrayList<ElementHelper<ClubPOJO>>();
		//All the clubs in the system (where we will search)
		List<ClubPOJO> clubs = usr.getPreferencesList();
		ElementHelper<ClubPOJO> aux;
		
		//First we make sure that the club contains the word introduced by the user
		for (ClubPOJO c : clubs) {
			aux = new ElementHelper<ClubPOJO>(c);
			aux.setVisible(c.getCommercialName().toLowerCase().contains(words.toLowerCase()));
			searchResults.add(aux);
		}
		
		//Then we check which clubs are a match for the given filters
		for (FilterPOJO f : filters) {
			FilterStrategy currentFilter = FilterMapper.mapFilter(f);

			for (ElementHelper<ClubPOJO> c : searchResults) {
				if(c.isVisible())
				c.setVisible(currentFilter.filter(c.getElement()));
			}
		}

		return searchResults;
	}

}