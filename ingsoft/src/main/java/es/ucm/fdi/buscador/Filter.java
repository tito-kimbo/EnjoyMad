package es.ucm.fdi.buscador;

import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.datos.dataobjects.Club;

public abstract class Filter {
	//CLASS? ABSTRACT CLASS?
	protected List<String> tags;
	
	public Filter(){}
	public List<String> getTags(){
		return tags;
	}
	
	//ARE ALL THE SET METHODS NECESSARY?
	
	public abstract boolean matches(Club c);
}
