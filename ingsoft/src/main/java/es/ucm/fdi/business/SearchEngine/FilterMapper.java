package es.ucm.fdi.business.SearchEngine;

import java.util.HashMap;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.SearchEngine.Filters.*;

/**
 * The function of this class is to create the 
 */
public class FilterMapper {
	private HashMap<String, FilterBuilder> map;
	
	public FilterMapper(){
		map = new HashMap<String, FilterBuilder>();
		
	}
	
	public Filter mapFilter(FilterPOJO fp){
		return map.get(fp.getID()).build(fp.getParams());
	}
}
