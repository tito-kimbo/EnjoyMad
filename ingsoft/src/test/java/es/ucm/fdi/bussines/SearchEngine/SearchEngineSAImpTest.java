package es.ucm.fdi.bussines.SearchEngine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.ucm.fdi.business.SearchEngine.SearchEngineSAImp;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.util.Element;
import es.ucm.fdi.integration.data.ClubPOJO;

public class SearchEngineSAImpTest {
	@Test
	public void SearchEngineTest(){
		List <FilterPOJO> filters = new ArrayList<FilterPOJO>();
		List <String> l1 = new ArrayList<String>();
		ClubPOJO c = new ClubPOJO("Teatro Kapital", "C/Falsa 1234", 20.30F, l1, 3.1F);
		SearchEngineSAImp se = new SearchEngineSAImp();
		se.search("kapi", filters);
		List<Element<ClubPOJO>> results = se.getSearchResults();
		assertTrue("Expected true",results.get(0).isVisible());
	}
}
