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
import es.ucm.fdi.business.searchengine.filters.FilterBO;

public class RatingFilter {
	
	@Test
	public void testRatingFilter(){
		Set<TagPOJO> l1 = new HashSet<TagPOJO>();
		l1.add(new TagPOJO("techno"));
		l1.add(new TagPOJO("reggaeton"));
		l1.add(new TagPOJO("electronica"));
		
		List <String> l2 = new ArrayList<String>();
		l2.add("4.0");
		FilterPOJO fp = new FilterPOJO("RatingFilter", l2);
		FilterMapper.addAll();
		FilterBO f = FilterMapper.mapFilter(fp);
		
		//The provisional ID in this test is the MD5 hash generated from the name
		ClubPOJO c = new ClubPOJO("aae032dec67f8f572570597421ad4b7e", "Mitty", 
				"C/Falsa 1234", 20.30F, l1);
		
		c.setRating(3.1F);
		assertFalse("Error when filtering by rating: rating lower "
				+ "than threshold should yield false", f.filter(c));
		
		c.setRating(4.0F);
		assertTrue("Error when filtering by rating: rating equal "
				+ "to threshold should yield true", f.filter(c));
		
		c.setRating(4.1F);
		assertTrue("Error when filtering by rating: rating higher "
				+ "than threshold should yield true", f.filter(c));
		
	}
}
