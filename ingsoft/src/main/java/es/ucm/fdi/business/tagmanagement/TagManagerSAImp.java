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
	private Set<TagPOJO> activeTags;
	
	/**
	 * Empty constructor for this class.
	 */
	public TagManagerSAImp(){
		activeTags = new HashSet<TagPOJO>();
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
	public void newTag(TagPOJO tp) {
		activeTags.add(tp);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void removeTags(TagPOJO tp){
		activeTags.remove(tp);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean hasTag(TagPOJO tp){
		return activeTags.contains(tp);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<TagPOJO> getActiveTags(){
		List<TagPOJO> list = new ArrayList<TagPOJO>();
		list.addAll(activeTags);
		return list;
	}

}
