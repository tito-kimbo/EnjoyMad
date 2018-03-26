package es.ucm.fdi.business.SearchEngine;

import java.util.HashMap;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.SearchEngine.Filters.Filter;
import es.ucm.fdi.business.SearchEngine.Filters.LocationFilter;
import es.ucm.fdi.business.SearchEngine.Filters.PriceFilter;
import es.ucm.fdi.business.SearchEngine.Filters.TagFilter;
import es.ucm.fdi.business.SearchEngine.Filters.ValorationFilter;

/**
 * The function of this class is to create the 
 */
public class FilterMapper {
	static private HashMap<String, Filter> map;
	
	public FilterMapper(){
		map = new HashMap<String, Filter>();
		map.put("PriceFilter", new PriceFilter());
		map.put("TagFilter", new TagFilter());
		map.put("ValorationFilter", new ValorationFilter());
		map.put("LocationFilter", new LocationFilter());
	}
	
	
	public static Filter mapFilter(FilterPOJO fp){
		return (Filter) map.get(fp.getID()).clone(fp);
	}
}