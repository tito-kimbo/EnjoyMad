package es.ucm.fdi.business.SearchEngine;

import java.util.HashMap;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.SearchEngine.Filters.*;

/**
 * The function of this class is to create the 
 */
public class FilterMapper {
	static private HashMap<String, FilterBuilder> map;
	
	//No need for constructor (will be static)
	
	public static Filter mapFilter(FilterPOJO fp){
		return map.get(fp.getID()).build(fp.getParams());
	}
}
