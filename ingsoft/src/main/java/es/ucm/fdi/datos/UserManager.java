package es.ucm.fdi.datos;

import java.util.Map;
import java.util.TreeMap;

import es.ucm.fdi.datos.dataobjects.User;
import es.ucm.fdi.exceptions.DuplicatedEntryException;
import es.ucm.fdi.exceptions.EntryNotFoundException;

//Specified in CRC
public class UserManager {
	protected Map<String, User> users; //Map > List -> O(1) en busqueda
	
	public UserManager(){
		users = new TreeMap<String,User>(); //Otras opciones a TreeMap?
	}
	
	public User getUser(String id){
		User aux = users.get(id);		
		return aux;
	}
	
	//NEEDS PARAMETER CHANGE TO USER type WHEN USER IMPLEMENTED
	public void insertUser(String id) throws DuplicatedEntryException{
		if(users.get(id) != null){
			throw new DuplicatedEntryException("The user already exists.");
		}
		else
		{
			//users.put(id, user);
		}
	}
	
	public void eliminateUser(String id) throws EntryNotFoundException {
		
		if(users.get(id) == null){
			throw new EntryNotFoundException("The user does not exist.");
		}else{
			users.remove(id);			
		}

	}
}
