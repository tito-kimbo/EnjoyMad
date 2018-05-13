package es.ucm.fdi.business.searchengine.filters;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * <code>Filter</code> that looks for matches in tags
 * 
 * @version 22.04.2018
 */
public class TagFilterStrategy implements FilterStrategy{
		
	//private TagManager tags;
	
	/**
	 * {@inheritDoc}
	 */
	public Object clone(FilterPOJO fp){
		//return new Object(FilterPOJO fp); //???
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean filter(ClubPOJO c){
		/*for(String tag : tags.getActiveTags()){
			if(!c.contains(tag)){
				//DOES THE TAG EXIST?				
				return false;
			}
		}*/
		
		return true;
	}

}
