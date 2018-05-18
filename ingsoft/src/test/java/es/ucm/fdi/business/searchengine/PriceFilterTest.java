package es.ucm.fdi.business.searchengine;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

import org.junit.Test;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.Location;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.business.searchengine.FilterMapper;

import es.ucm.fdi.business.searchengine.filters.FilterStrategy;

public class PriceFilterTest {
	@Test
	public void testPriceFilter() {
		// The provisional ID in this test is the MD5 hash generated from the
		// name
		ClubPOJO c = new ClubPOJO("af836aad1e889f499aa4d5a4aafd34cd", "Pacha",
				"C/Falsa 123", 20.30F, new Location(0, 0), 0);
		c.addTag(new TagPOJO("techno"));

		List<String> l2 = new ArrayList<String>();
		l2.add("30.50");

		FilterPOJO fp = new FilterPOJO("PriceFilter", l2);
		FilterMapper.addAll();

		FilterStrategy f = FilterMapper.mapFilter(fp);
		assertTrue("Error when filtering by price: club price lower "
				+ "than threshold should yield true" + l2.get(0),f.filter(c));

		c.setPrice(30.50F);
		assertTrue(
				"Error when filtering by price: equal prices should yield true",
				f.filter(c));

		c.setPrice(30.60F);
		assertFalse("Error when filtering by price: club price higher"
				+ " than threshold should yield false", f.filter(c));

	}
}
