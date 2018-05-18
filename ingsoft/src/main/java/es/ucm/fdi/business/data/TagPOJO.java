package es.ucm.fdi.business.data;

import java.io.Serializable;

/**
 * Container class for tags.
 * 
 * @version 18.05.2018
 */
public class TagPOJO implements Serializable{
	
	/**
	 * Generated serial UID.
	 */
	private static final long serialVersionUID = -6625517701029668443L;
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof TagPOJO)) {
			return false;
		}

		TagPOJO tagToCompare = (TagPOJO) o;

		return tag.equals(tagToCompare.getTag());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return tag.hashCode();
	}
	
}
