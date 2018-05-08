package es.ucm.fdi.business.data;

import java.io.Serializable;

/**
 * Container class for tags.
 * 
 * @version 04.05.2018
 */
public class TagPOJO implements Serializable{
	
	private String tag;
	
	public TagPOJO(String tag) {
		this.tag = tag;
	}

	/**
	 * Getter method for the tag.
	 */
	public String getTag() {
		return tag;
	}
	
	/**
	 * Setter method for the tag.
	 */
	public void setTag(String tag){
		this.tag = tag;
	}
	
}
