package es.ucm.fdi.bussines.searchengine;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.searchengine.filters.TagFilterStrategy;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.Location;

public class TagFilterTest {

	@Test
	public void basicTagFilterTest() {
		ClubPOJO c = new ClubPOJO("af836aad1e889f499aa4d5a4aafd34cd", "Pacha",
				"C/Falsa 123", 20.30F, new Location(0, 0), 0);
		TagFilterStrategy tf = new TagFilterStrategy(new ArrayList<TagPOJO>());

		assertTrue(
				"Error while filtering by tag: empty tag list should yield true every time.",
				tf.filter(c));
		tf = new TagFilterStrategy(Arrays.asList(new TagPOJO("Dubstep")));
		assertFalse(
				"Error while filtering by tag: non-matching one tag list should yield false.",
				tf.filter(c));

		c.addTag(new TagPOJO("Dubstep"));
		assertTrue(
				"Error while filtering by tag: matching one tag list should yield true.",
				tf.filter(c));
	}

	@Test
	public void multipleTagFilterTest() {
		ClubPOJO c = new ClubPOJO("af836aad1e889f499aa4d5a4aafd34cd", "Pacha",
				"C/Falsa 123", 20.30F, new Location(0, 0), 0);
		TagFilterStrategy tf = new TagFilterStrategy(Arrays.asList(new TagPOJO(
				"Dubstep"), new TagPOJO("Reggaeton")));

		c.addTag(new TagPOJO("Dubstep"));
		assertFalse(
				"Error while filtering by tag: partially matching list should yield false.",
				tf.filter(c));
		c.addTag(new TagPOJO("Reggaeton"));
		assertTrue(
				"Error while filtering by tag: fully matching list should yield true",
				tf.filter(c));
		tf = new TagFilterStrategy(Arrays.asList(new TagPOJO("Dubstep"),
				new TagPOJO("Reggaeton"), new TagPOJO("Rock")));
		c.addTag(new TagPOJO("Barata"));
		c.addTag(new TagPOJO("Elegante"));
		assertFalse(
				"Error while filtering by tag: partially matching list should yield false (club has more tags than list).",
				tf.filter(c));
	}
}
