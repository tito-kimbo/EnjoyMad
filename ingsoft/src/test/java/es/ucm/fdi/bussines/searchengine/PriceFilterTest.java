package es.ucm.fdi.bussines.searchengine;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.searchengine.FilterMapper;
import es.ucm.fdi.business.searchengine.filters.FilterBO;
import es.ucm.fdi.integration.data.ClubPOJO;

public class PriceFilterTest {
	@Test
	public void testPriceFilter(){
		Set <String> l1 = new HashSet<String>();
		l1.add("techno");
		l1.add("reggaeton");
		l1.add("electronica");
		
		//The provisional ID in this test is the MD5 hash generated from the name
		ClubPOJO c = new ClubPOJO("af836aad1e889f499aa4d5a4aafd34cd", "Pacha", "C/Falsa 123", 20.30F, l1);
		
		List <String> l2 = new ArrayList<String>();
		l2.add("30.50");
		
		FilterPOJO fp = new FilterPOJO("PriceFilter", l2);
		FilterMapper.addAll();
		FilterBO f = FilterMapper.mapFilter(fp);
		assertTrue("Expected true",f.filter(c));
		c.setPrice(30.50F);
		assertTrue("Expected true",f.filter(c));
		c.setPrice(30.60F);
		assertFalse("Expected false",f.filter(c));
		
	}
}
