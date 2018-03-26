package es.ucm.fdi.business.data;

import java.util.List;

import es.ucm.fdi.integration.data.DataPOJO;

/**
 * This class contains the information needed to build a certain <code>Filter</code>
 */
public class FilterPOJO extends DataPOJO {
	private List<String> params;
	
	public FilterPOJO(String id, List<String> p){
		super(id);
		params = p;
	}
	
	public List<String> getParams(){
		return params;
	}
}
