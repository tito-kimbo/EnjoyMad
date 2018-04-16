package es.ucm.fdi.business.SearchEngine;

import java.util.HashMap;
import java.lang.IllegalArgumentException;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.SearchEngine.Filters.*;

/**
 * The function of this class is to create the 
 */
public class FilterMapper {
	static private HashMap<String, FilterBO> map;
	
	//Static initializer for the class
	public static void addAll() {
		map = new HashMap<String, FilterBO>();
		map.put("PriceFilter", new PriceFilterBO());
		map.put("TagFilter", new TagFilterBO());
		map.put("RatingFilter", new RatingFilterBO());
		map.put("LocationFilter", new LocationFilterBO());	
	}
	
	public static void addFilter(String name, FilterBO prototype){
		map.put(name, prototype);
	}
	
	public static void removeFilter(String name){
		if(map.containsKey(name)){
			map.remove(name);
		}else{
			throw new IllegalArgumentException("Element to remove does not exist.");
		}
	}
	
	public static FilterBO mapFilter(FilterPOJO fp){
		return (FilterBO) map.get(fp.getID()).clone(fp);
	}
}
