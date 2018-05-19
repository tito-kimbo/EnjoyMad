package es.ucm.fdi.bussines.searchengine;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;
import org.junit.Assert.*;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;
import es.ucm.fdi.business.searchengine.SearchEngineSAImp;
import es.ucm.fdi.business.util.ElementHelper;;


public class SearchEngineSAImpTest {
	private ClubDAOImp cd;
	private SearchEngineSAImp searchMotor;

	/**
	 * @param id New user's id
	 * @param user New user's application name
	 * @param name New user's name
	 * 
	 * @return An user ready to be used for searching clubs.
	 */
	public UserPOJO readyToSearchUser(String id, String user, String name) 
	{
		LocalDate birthday = LocalDate.of(2018, Month.JANUARY, 1);
		
		UserPOJO usr = new UserPOJO(id, user, "password", "email@mail.com", name, birthday);
		
		usr.setPreferencesList(cd.getClubs());

		return usr;
	}

	/**
	 * TEST 1: Search without filters.
	 */
	@Test
	public void noFilterSearchTest() 
	{
		initializeDAOTest1();
		
		List<FilterPOJO> filters = new ArrayList<FilterPOJO>(); // Filters (empty)
		
		UserPOJO usr = readyToSearchUser("id", "frblazqu", "Francis");

		List<ElementHelper<ClubPOJO>> aux = searchMotor.search("", filters, usr);
		
		assertTrue("No filterSearch failed", aux.size()== 2);
	}
	private void initializeDAOTest1()
	{
		cd = new ClubDAOImp();

		Set<TagPOJO> l1 = new HashSet<TagPOJO>(); // Club1's tags (empty)
		Set<TagPOJO> l2 = new HashSet<TagPOJO>(); // Club2's tags (empty)
		
		ClubPOJO c1 = new ClubPOJO("Club1", "Teatro Kapital", "C/Falsa 1", 20.30F, l1);
		ClubPOJO c2 = new ClubPOJO("Club2", "Mitty", "C/Falsa 2", 15.00F, l2);
		
		cd.addClub(c1);
		cd.addClub(c2);
		
		searchMotor = new SearchEngineSAImp(cd);
	}
	
	
}
