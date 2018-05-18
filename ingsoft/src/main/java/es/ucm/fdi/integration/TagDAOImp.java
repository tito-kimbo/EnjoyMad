package es.ucm.fdi.integration;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import es.ucm.fdi.business.data.TagPOJO;

/**
 * 
 * @version 22.04.2018
 */
public class TagDAOImp implements TagDAO{
	private Set<TagPOJO> tags;
	
	/**
	 * Empty constructor for the set object of the DAO
	 */
	public TagDAOImp(){
		tags = Collections.newSetFromMap(new ConcurrentHashMap<TagPOJO, Boolean>());
	}

	/**
	 * @param stream get out a copy of the object of this DAO
	 * @throws IOException if I/O operations have an interruption
	 */
	synchronized private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeObject(tags);
	}
	
	/**
	 * {@inheritDoc}
	 */
	synchronized public List<TagPOJO> loadTags(){
		return new ArrayList<TagPOJO>(tags);
	}

	/**
	 * {@inheritDoc}
	 */
	synchronized public void saveTags(List<TagPOJO> list) {
		tags.addAll(list);
	}
}