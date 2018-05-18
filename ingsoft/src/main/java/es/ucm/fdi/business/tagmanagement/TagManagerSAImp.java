package es.ucm.fdi.business.tagmanagement;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.TagDAO;

/**
 * Implementation of the <code>TagManagerSA</code> service application based on
 * hashsets.
 *
 * @version 22.04.2018
 */
public class TagManagerSAImp implements TagManagerSA {
	private Set<TagPOJO> activeTags;
	private TagDAO tagAccess;

	/**
	 * Empty constructor for this class.
	 */
	public TagManagerSAImp(TagDAO tagAccess) {
		activeTags = new HashSet<TagPOJO>();
		this.tagAccess = tagAccess;
		load();
	}

	/**
	 * @param tagsList
	 *            is a List of <code>TagPOJO<code> with the activeTags
	 */
	public TagManagerSAImp(List<TagPOJO> tagList) {
		activeTags = new HashSet<TagPOJO>(tagList);
	}

	/**
	 * {@inheritDoc}
	 */
	public void load() {
		tagAccess.saveTags(new ArrayList<TagPOJO>(activeTags));
	}

	/**
	 * {@inheritDoc}
	 */
	public void save() {
		activeTags = new HashSet<TagPOJO>(tagAccess.loadTags());
	}

	/**
	 * {@inheritDoc}
	 */
	synchronized public void newTag(TagPOJO tp) {
		activeTags.add(tp);
	}

	/**
	 * {@inheritDoc}
	 */
	synchronized public void removeTags(TagPOJO tp) {
		activeTags.remove(tp);
	}

	/**
	 * {@inheritDoc}
	 */
	synchronized public boolean hasTag(TagPOJO tp) {
		return activeTags.contains(tp);
	}

	/**
	 * {@inheritDoc}
	 */
	synchronized public List<TagPOJO> getActiveTags() {
		List<TagPOJO> list = new ArrayList<TagPOJO>();
		list.addAll(activeTags);
		return list;
	}

}