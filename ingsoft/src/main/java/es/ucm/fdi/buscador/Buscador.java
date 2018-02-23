package es.ucm.fdi.buscador;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import es.ucm.fdi.buscador.Filter;

/**
 * This class
 * @authors tito_kimbo @and jorgitou98
 */
public class Buscador 
{
	//DATABASE AS ATTRIBUTE?
	//Methods? Needs big data compatibility
	
	//Lets suppose a single user for testing
	//This class is to be implemented externally or substituted by the User class
	private class UserData{
		private Map<String, Integer> tagSearched;
		private List<String> top5Tags;
		
		public UserData(){}
		
		public void addEntry(String key, int value){
			tagSearched.put(key, tagSearched.get(key)+value);
		}
		
		private void calculateTop5(){
			//ITERATE OVER ARRAY
			//IF ON TOP 5, SAVE
		}
	}
	
	private class Club{ //MOCK CLASS FOR TESTING
		private int entryPrice;
		private String name;
		private List<String> tags;
		private boolean show;
		
		public Club(String n, int p, String[] args){
			entryPrice = p;
			name = n;
			tags = new ArrayList<String>();
			for(String x : args){
				tags.add(x);
			}
		}
		
		public boolean getShow(){
			return show;
		}
		
		public void setShow(boolean b){
			show = b;
		}
	}
	
	UserData data; //Initialize
	
	String[] tags = {"pop", "modern"};
	Club c = new Club("ClubTest", 10, tags);
	
	Filter selectedFilter;
	
	public Buscador(){}
	
	/**
	 * Given a club and a user, returns the value for the given user
	 * @return
	 */
	private int assignValue(){
		return 0;
	}
		
	/**
	 * Given a user and the list of available clubs, associates the user to its suggestions
	 * in a Hashtable.
	 * The suggestions are pairs <club, value> or <clubID, value> that cover all the possible
	 * clubs
	 */
	public void calculateSuggestions(){
		assignValue();
	}
	
	/*
	 * Filters the suggestions through the selected criteria
	 */
	public void showFilteredSuggestions(){
	}
	
}
