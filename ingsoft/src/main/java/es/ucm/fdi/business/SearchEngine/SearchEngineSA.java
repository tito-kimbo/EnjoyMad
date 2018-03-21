package es.ucm.fdi.business.SearchEngine;

import java.util.List;

import es.ucm.fdi.business.data.FilterPOJO;

/**
 * 
 *
 */
public interface SearchEngineSA {
	
	
	public class Element<T>{
		private boolean visible;
		private T element;
		
		public Element(T e){
			visible = false;
			element = e;
		}
		
		public boolean isVisible(){
			return visible;
		}
		
		public T getElement(){
			return element;
		}
		
		protected void setVisible(boolean b){
			visible = b;
		}
	}
	
	
	/**
	 * 
	 * @param words
	 * @param filters
	 */
	public void search(List<String> words, List<FilterPOJO> filters);

}
