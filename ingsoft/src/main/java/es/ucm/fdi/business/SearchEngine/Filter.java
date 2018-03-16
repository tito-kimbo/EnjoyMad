package es.ucm.fdi.business.SearchEngine;

import es.ucm.fdi.data.ClubPOJO;

public abstract class Filter {

	boolean filter(ClubPOJO c){
		return false;
	}
}
