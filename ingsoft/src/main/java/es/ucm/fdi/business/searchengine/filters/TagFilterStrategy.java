package es.ucm.fdi.business.searchengine.filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * <code>Filter</code> that looks for matches in tags
 * 
 * @version 22.04.2018
 */
public class TagFilterStrategy implements FilterStrategy{
	private List<TagPOJO> tags;

	public boolean filter(ClubPOJO c) {
		for(String tag : tags){
			if(!c.contains(tag.getTag())){
				//DOES THE TAG EXIST?				
				return false;
			}
		return true;
	}

	public Object clone(FilterPOJO fp) {
		//Pending
		// TODO Auto-generated method stub
		return null;
	}

}
