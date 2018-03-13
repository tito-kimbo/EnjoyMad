package es.ucm.fdi.SearchEngine;

import es.ucm.fdi.datos.ClubPOJO;

public abstract class Filter {

	boolean filter(ClubPOJO c){
		return false;
	}
}
