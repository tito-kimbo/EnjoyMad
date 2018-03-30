package es.ucm.fdi.bussines.SearchEngine;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.ucm.fdi.business.SearchEngine.FilterMapper;
import es.ucm.fdi.business.SearchEngine.Filters.Filter;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

public class PriceFilterTest {
	@Test
	public void testPriceFilter(){
		List <String> l1 = new ArrayList<String>();
		l1.add("tecno");
		l1.add("reggaeton");
		l1.add("electronica");
		ClubPOJO c = new ClubPOJO("Pacha", "C/Falsa 123", 20.30F, l1);
		FilterMapper fm = new FilterMapper();
		List <String> l2 = new ArrayList<String>();
		l2.add("30.50");
		FilterPOJO fp = new FilterPOJO("PriceFilter", l2);
		Filter f = FilterMapper.mapFilter(fp);
		assertTrue("Expected true",f.filter(c));
		c  = new ClubPOJO("Pacha", "C/Falsa 123", 30.50F, l1);
		assertTrue("Expected true",f.filter(c));
		c  = new ClubPOJO("Pacha", "C/Falsa 123", 30.60F, l1);
		assertFalse("Expected false",f.filter(c));
		
	}
}
