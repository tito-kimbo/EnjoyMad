package es.ucm.fdi.business.tagmanagement;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import es.ucm.fdi.business.data.TagPOJO;

/**
 *	Implementation of the <code>TagManagerSA</code> service application based on hashsets.
 *
 *	@version 22.04.2018
 */
public class TagManagerSAImp implements TagManagerSA {
	private Set<String> activeTags;
	
	/**
	 * Empty constructor for this class.
	 */
	public TagManagerSAImp(){
		activeTags = new HashSet<String>();
		load();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void load(){
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void save(){
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void addTags(TagPOJO tp){
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void removeTags(TagPOJO tp){
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean hasTag(String s){
		return activeTags.contains(s);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<String> getActiveTags(){
		List<String> list = new ArrayList<String>();
		list.addAll(activeTags);
		return list;
	}
}
