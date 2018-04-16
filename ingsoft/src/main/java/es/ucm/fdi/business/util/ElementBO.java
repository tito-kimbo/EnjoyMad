package es.ucm.fdi.business.util;

/**
 * This class is designed to hold search results, indicating whether a possible match is
 * or not visible in the current search.
 *
 * @param <T> Type of the content of the class
 */
public class ElementBO<T>{
	private boolean visible;
	private T element;
	
	public ElementBO(T e){
		visible = false;
		element = e;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public T getElement(){
		return element;
	}
	
	public void setVisible(boolean b){
		visible = b;
	}
}
