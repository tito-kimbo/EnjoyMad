package es.ucm.fdi.bussines.searchengine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.business.searchengine.FilterMapper;

import es.ucm.fdi.business.searchengine.filters.FilterStrategy;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.Location;

public class RatingFilter {
	
	@Test
	public void testRatingFilter(){
		List <String> l2 = new ArrayList<String>();
		l2.add("4.0");
		FilterPOJO fp = new FilterPOJO("RatingFilter", l2);
		FilterMapper.addAll();
		FilterStrategy f = FilterMapper.mapFilter(fp);
		
		//The provisional ID in this test is the MD5 hash generated from the name
		ClubPOJO c = new ClubPOJO("aae032dec67f8f572570597421ad4b7e", "Mitty", 
				"C/Falsa 1234", 20.30F, new Location(0,0), 0);
		
		c.addTag(new TagPOJO("techno"));
		c.addTag(new TagPOJO("reggaeton"));
		c.addTag(new TagPOJO("electronica"));
		
		c.setRating(3.1F);
		assertFalse("Expected false",f.filter(c));
		c.setRating(4.1F);
		assertTrue("Expected true",f.filter(c));
		
	}
}
