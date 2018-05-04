package es.ucm.fdi.bussines.searchengine;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.searchengine.FilterMapper;
import es.ucm.fdi.business.searchengine.SearchEngineSA;
import es.ucm.fdi.business.searchengine.SearchEngineSAImp;
import es.ucm.fdi.business.util.ElementBO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

public class SearchEngineSAImpTest {
	
	private static ClubDAOImp cd;
	
	static {
		cd = new ClubDAOImp();
	}
	
	public static UserPOJO readyToSearchUser()
	{
		UserPOJO usr = new UserPOJO("usr", "frblazqu", "password", "email@mail.com", "Francis", 
				                    LocalDate.of(2018, Month.JANUARY, 1));
		usr.setPreferencesList(cd.getClubs());
		
		return usr;
	}
	
	
	@Test
	public void noFilterSearchTest(){
		List <FilterPOJO> filters = new ArrayList<FilterPOJO>(); //Filters     (empty)
		Set <String> l1 = new HashSet<String>();				 //Club's tags (empty)
		
		//Clubs to search
		ClubPOJO c = new ClubPOJO("a2868521eac27ed8c7c4d7a38051f912", 
				     "Teatro Kapital", "C/Falsa 1234", 20.30F, l1);	
		c.setRating(3.1F);
		
		//This make the clubs accessible
		cd.addClub(c);
		//ud.add usuarios
		
		//This initializes the filters
		FilterMapper.addAll();
		
		//Ready to search
		SearchEngineSA se = new SearchEngineSAImp(cd);
		
		List<ElementBO<ClubPOJO>> results = se.search("kapi", filters, readyToSearchUser());
		
		//There isn't filters, so the result must be all the clubs (just one in this case)
		assertTrue("Expected true",results.get(0).isVisible());
	}
}
