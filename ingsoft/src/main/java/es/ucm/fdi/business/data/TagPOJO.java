package es.ucm.fdi.business.data;

import java.io.Serializable;
import java.util.List;

/**
 * Container class for tags.
 * 
 * @version 22.04.2018
 */
public class TagPOJO implements Serializable{
	
	private String tag;

	private List<String> tags;
	
	
	public TagPOJO(String tag, List<String> tags) {
		super();
		this.tag = tag;
		this.tags = tags;
	}

	/**
	 * Getter method for the tag
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
