package es.ucm.fdi.business.SearchEngine.Filters;

import java.util.List;

public interface FilterBuilder {

	public Filter build(List<String> params);
}
