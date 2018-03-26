package es.ucm.fdi.integration;

import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.ClubPOJO;

public class ClubDAOImp implements ClubDAO {
	List<ClubPOJO> clubs;
	
	private int getclub(String id, int ini, int last) {
		if(ini == last)
			return ini;
		int mid = (last + ini)/2;
		return (clubs.get(mid).getID().compareTo(id) < 0) ? getclub(id,mid+1,last) : getclub(id,ini,mid);
	}
	public ClubDAOImp() {
		clubs = new ArrayList<ClubPOJO>();
	}
	public ClubPOJO getClub(String id) {
		return clubs.get(getclub(id,0,clubs.size()-1));
	}
	public void insert(ClubPOJO club) {
		//clubs.add(club,getclub(club.getId(),0,clubs.size()-1));
		clubs.add(club);
	}
	public List<ClubPOJO> getClubs() {return clubs;}
	public ClubPOJO getUser(String id){return null;}
}
