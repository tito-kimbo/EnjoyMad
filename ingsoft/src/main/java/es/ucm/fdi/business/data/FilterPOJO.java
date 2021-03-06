package es.ucm.fdi.business.data;

import java.util.List;

import es.ucm.fdi.integration.data.DataPOJO;

/**
 * This class contains the information needed to build a certain
 * <code>Filter</code>
 * 
 * @version 25.05.2018
 */
public class FilterPOJO extends DataPOJO {
	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = 7280573179865244661L;
	private List<String> params;

	/**
	 * Constructor for this class.
	 * 
	 * @param id
	 *            <code>Filter</code> id.
	 * @param p
	 *            List of parameters needed to build the <code>Filter</code>.
	 */
	public FilterPOJO(String id, List<String> p) {
		super(id);
		params = p;
	}

	/**
	 * Getter method for the list of parameters.
	 * 
	 * @return l List of parameters required to build the <code>Filter</code>.
	 */
	public List<String> getParams() {
		return params;
	}
}
