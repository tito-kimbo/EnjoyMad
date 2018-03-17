package es.ucm.fdi.business.SearchEngine;

import es.ucm.fdi.data.ClubPOJO;

public abstract class Filter {

	public abstract boolean filter(ClubPOJO c);
}
