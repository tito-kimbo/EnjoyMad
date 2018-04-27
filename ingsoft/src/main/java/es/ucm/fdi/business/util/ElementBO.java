package es.ucm.fdi.business.util;

/**
 * This class is designed to hold search results, indicating whether a possible match is
 * or not visible in the current search.
 *
 * @param <T> Type of the content of the class.
 * @version 22.04.2018
 */
public class ElementBO<T>{
	private boolean visible;
	private T element;
	
	/**
	 * Constructor for this class.
	 * 
	 * @param e	Element contained in this object.
	 */
	public ElementBO(T e){
		visible = false;
		element = e;
	}
	
	/**
	 * Getter method for {@link #visible}.
	 * 
	 * @return Whether the current element is visible in the search.
	 */
	public boolean isVisible(){
		return visible;
	}
	
	/**
	 * Getter method for {@link #element}.
	 * 
	 * @return The element contained in this object.
	 */
	public T getElement(){
		return element;
	}
	
	/**
	 * Setter method for {@link #visible}.
	 * 
	 * @param b	New value for the attribute.
	 */
	public void setVisible(boolean b){
		visible = b;
	}
}
