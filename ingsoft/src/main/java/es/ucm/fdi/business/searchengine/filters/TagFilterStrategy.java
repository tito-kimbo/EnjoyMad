package es.ucm.fdi.business.searchengine.filters;

import java.util.List;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * <code>Filter</code> that looks for matches in tags
 * 
 * @version 22.04.2018
 */
public class TagFilterStrategy implements FilterStrategy{
	private List<TagPOJO> tags;

	public boolean filter(ClubPOJO c) {
		for(TagPOJO tag : tags){
			if(!c.getTags().contains(tag)){
				//DOES THE TAG EXIST?				
				return false;
			}
		}
		return true;
	}

	public Object clone(FilterPOJO fp) {
		//Pending
		// TODO Auto-generated method stub
		return null;
	}

}
