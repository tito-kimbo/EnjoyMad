package es.ucm.fdi.integration;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import es.ucm.fdi.integration.data.ClubPOJO;
/**
 * This class is a Club data access object that implements {@link ClubDAO}.
 * @author Fco Borja 
 * @author Carlijn
 */
public class ClubDAOImp implements ClubDAO {
	List<ClubPOJO> clubs;
	
	/**
	 * Constructor of the ClubDAO. Sets the list of clubs empty.
	 */
	public ClubDAOImp() {
		clubs = new ArrayList<ClubPOJO>();
	}
	/**
	 * Returns the club that matches the identification in the positions of the list between the 'ini' position and the 'last' position.
	 * @param id identification
	 * @param ini initial position of the search
	 * @param last last position of the search
	 * @return club that matches the identification in the range
	 * @throws NoSuchElementException if there is no element matching the identification
	 */
	private int getclub(String id, int ini, int last) throws NoSuchElementException {
		if(ini == last)
			if(clubs.get(ini).getID() == id)
				return ini;
			else 
				throw new NoSuchElementException();
		int mid = (last + ini)/2;
		return (clubs.get(mid).getID().compareTo(id) < 0) ? getclub(id,mid+1,last) : getclub(id,ini,mid);
	}
	/**
	 * @inheritDoc
	 */
	public ClubPOJO getClub(String id) throws NoSuchElementException {
		return clubs.get(getclub(id,0,clubs.size()-1));
	}
	/**
	 * @inheritDoc
	 */
	public void insert(ClubPOJO club) {
		clubs.add(club);
	}
	/**
	 * @inheritDoc
	 */
	public List<ClubPOJO> getClubs() {return clubs;}
}
