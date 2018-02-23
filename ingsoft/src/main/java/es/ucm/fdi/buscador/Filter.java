package es.ucm.fdi.buscador;

import java.util.List;
import java.util.ArrayList;

public class Filter {
	//CLASS? ABSTRACT CLASS?
	private List<String> tags;
	private int distance, price;
	
	public Filter(){}
	
	public int getDistance(){
		return distance;
	}
	
	public int getPrice(){
		return price;
	}
	
	public List<String> getTags(){
		return tags;
	}
	
	//ARE ALL THE SET METHODS NECESSARY?
}
