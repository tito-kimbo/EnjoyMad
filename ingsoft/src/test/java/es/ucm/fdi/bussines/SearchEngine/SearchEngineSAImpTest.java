package es.ucm.fdi.bussines.SearchEngine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;

import es.ucm.fdi.business.SearchEngine.FilterMapper;
import es.ucm.fdi.business.SearchEngine.SearchEngineSA;
import es.ucm.fdi.business.SearchEngine.SearchEngineSAImp;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.util.ElementBO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;

public class SearchEngineSAImpTest {
	@Test
	public void SearchEngineTest(){
		List <FilterPOJO> filters = new ArrayList<FilterPOJO>();
		Set <String> l1 = new HashSet<String>();
		ClubPOJO c = new ClubPOJO("a2868521eac27ed8c7c4d7a38051f912", "Teatro Kapital",
				"C/Falsa 1234", 20.30F, l1);	
		c.setRating(3.1F);
		
		ClubDAOImp cd = new ClubDAOImp();
		cd.addClub(c);
		
		SearchEngineSA se = new SearchEngineSAImp(cd);
		FilterMapper.addAll();
		se.search("kapi", filters);
		List<ElementBO<ClubPOJO>> results = se.getSearchResults();
		assertTrue("Expected true",results.get(0).isVisible());
	}
}
