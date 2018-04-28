package es.ucm.fdi.integration;

import java.util.List;

import es.ucm.fdi.business.data.TagPOJO;

/**
 * 
 * 
 * @version 22.04.2018
 */
public interface TagDAO {
	public List<TagPOJO> loadTags();
	public void saveTags(List<TagPOJO> l);
}
