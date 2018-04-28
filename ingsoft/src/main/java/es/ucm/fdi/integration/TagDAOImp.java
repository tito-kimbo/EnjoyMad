package es.ucm.fdi.integration;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.business.data.TagPOJO;

/**
 * 
 * @version 22.04.2018
 */
public class TagDAOImp implements TagDAO{
	public List<TagPOJO> loadTags(){
		return new ArrayList<TagPOJO>();
	}

	public void saveTags(List<TagPOJO> l) {
		for (TagPOJO tp: l){
			// Guardo en base de datos
		}
		
	}
}
