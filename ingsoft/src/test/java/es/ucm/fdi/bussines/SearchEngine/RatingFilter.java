package es.ucm.fdi.bussines.SearchEngine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.ucm.fdi.business.SearchEngine.FilterMapper;
import es.ucm.fdi.business.SearchEngine.Filters.Filter;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

public class RatingFilter {
	@Test
	public void testRatingFilter(){
		List <String> l1 = new ArrayList<String>();
		l1.add("tecno");
		l1.add("reggaeton");
		l1.add("electronica");
		ClubPOJO c = new ClubPOJO("Mitty", "C/Falsa 1234", 20.30F, l1, 3.1F);
		FilterMapper fm = new FilterMapper();
		List <String> l2 = new ArrayList<String>();
		l2.add("4.0");
		FilterPOJO fp = new FilterPOJO("RatingFilter", l2);
		Filter f = FilterMapper.mapFilter(fp);
		assertFalse("Expected false",f.filter(c));
		c  = new ClubPOJO("Pacha", "C/Falsa 123", 30.50F, l1, 4.1F);
		assertTrue("Expected true",f.filter(c));
		
	}
}
