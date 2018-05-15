package es.ucm.fdi.business.searchengine.filters;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * <code>Filter</code> that looks for matches in tags
 * 
 * @version 15.05.2018
 */
public class TagFilterStrategy implements FilterStrategy{
	private List<TagPOJO> tags;

	/**
	 * Empty constructor for <code>FilterMapper</code>
	 */
	public TagFilterStrategy(){}
	
	/**
	 * Constructor with list of tags.
	 * 
	 * @param tags
	 */
	public TagFilterStrategy(List<TagPOJO> tags){
		this.tags = tags;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean filter(ClubPOJO c) {
		for(TagPOJO tag : tags){
			if(!c.getTags().contains(tag)){
				//DOES THE TAG EXIST?				
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object clone(FilterPOJO fp) {
		List<TagPOJO> filterTags = new ArrayList<TagPOJO>();
		
		for(String s : fp.getParams()){
			filterTags.add(new TagPOJO(s));
		}
		
		return new TagFilterStrategy(filterTags);
	}

}
