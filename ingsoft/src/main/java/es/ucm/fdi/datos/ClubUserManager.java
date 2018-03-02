package es.ucm.fdi.datos;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import es.ucm.fdi.buscador.Filter;
import es.ucm.fdi.datos.dataobjects.Club;
import es.ucm.fdi.exceptions.DuplicatedEntryException;
import es.ucm.fdi.exceptions.EntryNotFoundException;

//Specified in CRC
public class ClubUserManager extends UserManager {
	protected Map<String,Club> clubs;
	
	public ClubUserManager(){
		clubs = new TreeMap<String,Club>();
	}
	
	public Club getClub(String id){
		return clubs.get(id);
	}
	
	//REQUIRES CHANGE TO CLUB WHEN CLUB IS IMPLEMENTED
	public void insertClub(String id) throws DuplicatedEntryException{
		if(clubs.get(id) != null){
			throw new DuplicatedEntryException("The club already exists.");
		}else{
			//clubs.put(id, club);
		}
	}
	
	public void eliminateClub(String id) throws EntryNotFoundException{
		if(clubs.get(id) == null){
			throw new EntryNotFoundException("The club does not exist.");
		}else{
			clubs.remove(id);
		}
	}
	
	public List<Club> getMatchingClubs(Filter f){
		List<Club> matching = new ArrayList<Club>();
		
		//Check documentation for Maps
		for(Club c : clubs){
			if(f.matches(c)){
				matching.add(c);
			}
		}
		
		return matching;
	}
}
