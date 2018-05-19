package es.ucm.fdi.business.searchengine;

import java.util.HashMap;
import java.lang.IllegalArgumentException;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.searchengine.filters.*;

/**
 * The function of this class is to solve the problem of <code>Filter</code>
 * creation.
 * 
 * @version 22.04.2018
 */
public class FilterMapper {
	
	private static HashMap<String, FilterStrategy> map = new HashMap<String, FilterStrategy>();

	//Public access keys for the filters
	public static String PRICE_FILTER    = "PriceFilter";
	public static String TAG_FILTER      = "TagFilter";
	public static String RATING_FILTER   = "RatingFilter";
	public static String LOCATION_FILTER = "LocationFilter";
	
	
	//Static initializer for the class
	/**
	 * Adds all existing <code>Filters</code> to the map.
	 */
	public static void addAll() {
		map.put(PRICE_FILTER,    new PriceFilterStrategy());
		map.put(TAG_FILTER,      new TagFilterStrategy());
		map.put(RATING_FILTER,   new RatingFilterStrategy());
		map.put(LOCATION_FILTER, new LocationFilterStrategy());
	}

	/**
	 * Adds a specific <code>Filter</code> to the map.
	 * 
	 * @param name      <code>Filter</code> identifier.
	 * @param prototype Prototype object for the <code>Filter</code>.
	 */
	public static void addFilter(String name, FilterStrategy prototype){
		map.put(name, prototype);
	}

	/**
	 * Removes a specific <code>Filter</code> from the map.
	 * 
	 * @param name Identifier of the <code>Filter</code> to remove.
	 */
	public static void removeFilter(String name) {
		if (map.containsKey(name)) {
			map.remove(name);
		} else {
			throw new IllegalArgumentException("Element to remove does not exist.");
		}
	}

	/**
	 * Builds a certain <code>Filter</code> from the information given.
	 * 
	 * @param fp Object containing the information of the <code>Filter</code> to
	 *           create.
	 * @return The newly built <code>Filter</code>.
	 */
	public static FilterStrategy mapFilter(FilterPOJO fp){
		return (FilterStrategy) map.get(fp.getID()).clone(fp);
	}
}