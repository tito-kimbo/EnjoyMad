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
import es.ucm.fdi.business.searchengine.filters.FilterStrategy;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.Location;

public class PriceFilterTest {
	@Test
	public void testPriceFilter(){
		//The provisional ID in this test is the MD5 hash generated from the name
		ClubPOJO c = new ClubPOJO("af836aad1e889f499aa4d5a4aafd34cd", "Pacha", "C/Falsa 123", 20.30F, new Location(0,0), 0);
		c.addTag("techno");
		c.addTag("techno");
		c.addTag("techno");
		
		List <String> l2 = new ArrayList<String>();
		l2.add("30.50");
		
		FilterPOJO fp = new FilterPOJO("PriceFilter", l2);
		FilterMapper.addAll();
		FilterStrategy f = FilterMapper.mapFilter(fp);
		assertTrue("Expected true",f.filter(c));
		c.setPrice(30.50F);
		assertTrue("Expected true",f.filter(c));
		c.setPrice(30.60F);
		assertFalse("Expected false",f.filter(c));
		
	}
}
