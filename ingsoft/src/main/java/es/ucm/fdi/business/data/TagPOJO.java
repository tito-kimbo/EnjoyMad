package es.ucm.fdi.business.data;

import java.util.List;

/**
 * Container class for tags.
 * 
 * @version 22.04.2018
 */
public class TagPOJO {
	
	private String tag;
	
	private List<String> tags;

	/**
	 * Getter method for the tag array.
	 */
	public List<String> getTags() {
		return tags;
	}
	
	/**
	 * Setter method for the tag array.
	 */
	public void setTags(List<String> tags){
		this.tags = tags;
	}
	
}
