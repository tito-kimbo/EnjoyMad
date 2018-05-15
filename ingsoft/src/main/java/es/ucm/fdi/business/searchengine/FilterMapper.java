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
	static private HashMap<String, FilterBO> map = new HashMap<String, FilterBO>();

	// Static initializer for the class
	/**
	 * Adds all existing <code>Filters</code> to the map.
	 */
	public static void addAll() {
		map.put("PriceFilter", new PriceFilterBO());
		map.put("TagFilter", new TagFilterBO());
		map.put("RatingFilter", new RatingFilterBO());
		map.put("LocationFilter", new LocationFilterBO());
	}

	/**
	 * Adds a specific <code>Filter</code> to the map.
	 * 
	 * @param name
	 *            <code>Filter</code> identifier.
	 * @param prototype
	 *            Prototype object for the <code>Filter</code>.
	 */
	public static void addFilter(String name, FilterBO prototype) {
		map.put(name, prototype);
	}

	/**
	 * Removes a specific <code>Filter</code> from the map.
	 * 
	 * @param name
	 *            Identifier of the <code>Filter</code> to remove.
	 */
	public static void removeFilter(String name) {
		if (map.containsKey(name)) {
			map.remove(name);
		} else {
			throw new IllegalArgumentException(
					"Element to remove does not exist.");
		}
	}

	/**
	 * Builds a certain <code>Filter</code> from the information given.
	 * 
	 * @param fp
	 *            Object containing the information of the <code>Filter</code>
	 *            to create.
	 * @return The newly built <code>Filter</code>.
	 */
	public static FilterBO mapFilter(FilterPOJO fp) {
		return (FilterBO) map.get(fp.getID()).clone(fp);
	}
}