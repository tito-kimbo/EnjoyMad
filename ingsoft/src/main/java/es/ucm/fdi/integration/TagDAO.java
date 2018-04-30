package es.ucm.fdi.integration;

import java.util.List;

import es.ucm.fdi.business.data.TagPOJO;

/**
 * 
 * 
 * @version 22.04.2018
 */
public interface TagDAO {
	
	/**
	 * @return the tags loaded in the system with a List of <code>TagPOJO<code>
	 */
	public List<TagPOJO> loadTags();
	
	/**
	 * 
	 * @param list with the new tags will be added
	 */
	public void saveTags(List<TagPOJO> list);
}
