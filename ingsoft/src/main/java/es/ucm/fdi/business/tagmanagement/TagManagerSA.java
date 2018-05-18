package es.ucm.fdi.business.tagmanagement;

import es.ucm.fdi.business.data.TagPOJO;

/**
 * Interface that provides the main functionality for tag system management.
 * 
 *	@version 22.04.2018
 */
public interface TagManagerSA {
	
	/**
	 * Loads current active tags.
	 */
	public void load();
	
	/**
	 * Saves current active tags.
	 */
	public void save();
	
	/**
	 * Adds a tag to the active tag list.
	 * 
	 * @param tp POJO representing the tag to add.
	 */
	public void newTag(TagPOJO tp);
	
	/**
	 * Removes a tag from the active list.
	 * 
	 * @param tp POJO representing the tag to add.
	 */
	public void removeTags(TagPOJO tp);
	
	/**
	 * @param tp POJO representing the tag to add.
	 * @return true if the active list has the tag {@link tp}
	 */
	public boolean hasTag(TagPOJO tp);
	
}
