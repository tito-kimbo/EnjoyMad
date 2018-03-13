package es.ucm.fdi.datos;

import java.util.List;
import java.util.ArrayList;

public class ClubDAOImp implements ClubDAO {
	List<ClubPOJO> clubs;
	
	private int getclub(String id, int ini, int last) {
		if(ini == last)
			return ini;
		int mid = (last + ini)/2;
		return (clubs.get(mid) < id) ? getclub(id,mid+1,last) : getclub(id,ini,mid);
	}
	public ClubDAOImp() {
		clubs = new ArrayList<ClubPOJO>();
	}
	public ClubPOJO getclub(String id) {
		return clubs.get(getclub(id,0,clubs.size()-1));
	}
	public void insert(ClubPOJO club) {
		clubs.add(club,getclub(club.getID(),0,clubs.size()-1));
	}
	public List<ClubPOJO> getUsers() {return clubs;}
	public List<ClubPOJO> getMatchingClubs(List<String> tags){
		
	}
	public List<ClubPOJO> getMatchingClubs(int minPrice, int maxPrice){
		List<ClubPOJO> list = new ArrayList<ClubPOJO>();
		for(ClubPOJO c : clubs)
			if(c.getPrice() <= maxPrice && c.getPrice() >= maxPrice)
				list.add(c);
		return list;
	}
	public List<ClubPOJO> getMatchingClubs(String location, int maxRange){
		/*
		 * list := {}
		 * for each club in clubs
		 * 		Using Google Map's API get the distance from club to the location
		 * 		if distance is less or equal than maxRange add to list
		 * return list
		 */
	}
}
