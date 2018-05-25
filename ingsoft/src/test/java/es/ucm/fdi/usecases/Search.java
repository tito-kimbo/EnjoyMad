package es.ucm.fdi.usecases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.ProductionConfig;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.requesthandling.tools.RequestType;
import es.ucm.fdi.business.util.ElementHelper;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/*
 * The use case executes as follows
 * 
 * 1. User inserts relevant data (this part is assumed)
 * 2. request() method from FrontController is called
 * 		2.* FrontController handles request internally on separate thread
 * 3. While there is no answer, the user polls (calls poll()) at fixed time intervals
 * 5. Valid answer is given at some point
 */
public class Search {
	public static final int POLLING_DELAY = 100;
	public static List<ClubPOJO> clubs = Arrays.asList(
			new ClubPOJO("c1", "club1", "C/Falsa 1", 10.0f,
					new HashSet<TagPOJO>(Arrays
							.asList(new TagPOJO("pop"),
									new TagPOJO("elegante"), new TagPOJO(
											"electronica")))),
			new ClubPOJO("c2", "club2", "C/Falsa 2", 15.0f,
					new HashSet<TagPOJO>(Arrays
							.asList(new TagPOJO("reggaeton")))),
			new ClubPOJO("c3", "club3", "C/Falsa 3", 25.0f,
					new HashSet<TagPOJO>(Arrays.asList(new TagPOJO("pop"),
							new TagPOJO("rock"), new TagPOJO("hardstyle"),
							new TagPOJO("reggae")))));
	public static List<String> tags = Arrays.asList("pop", "elegante", "rock",
			"reggae", "hardstyle", "electronica", "reggaeton");

	@Before
	public void setUp() {
		// INITIALIZATION
		ProductionConfig.init(false);
		UserPOJO user = new UserPOJO("id", "user", "Pass123/",
				"user@gmail.com", "Jorge Ramírez Gallardo", LocalDate.of(1986,
						Month.MARCH, 17));
		for (String s : tags) {
			ProductionConfig.getFrontController().getTagManagerSA()
					.newTag(new TagPOJO(s));
		}

		for (ClubPOJO c : clubs) {
			ProductionConfig.getFrontController().getProfileManagerSA()
					.addNewClub(c);
		}

		Map<TagPOJO, Integer> vals = new HashMap<TagPOJO, Integer>();
		vals.put(new TagPOJO("pop"), 5);
		vals.put(new TagPOJO("elegante"), 2);
		vals.put(new TagPOJO("electronica"), 10);
		vals.put(new TagPOJO("reggae"), 0);
		vals.put(new TagPOJO("rock"), 3);
		vals.put(new TagPOJO("hardstyle"), 15);
		vals.put(new TagPOJO("reggaeton"), 6);
		user.setValueTags(vals);
		ProductionConfig.getFrontController().getProfileManagerSA()
				.addNewUser(user);
		
		ProductionConfig.getFrontController().getCustomDataSA().updateValues();	
	}

	public RequestPOJO buildRP(String userID) {
		List<Object> params = new ArrayList<Object>();
		params.add("id");
		params.add("3");
		params.add(Arrays.asList(new FilterPOJO(ProductionConfig.TAG_FILTER,
				Arrays.asList("hardstyle", "reggaeton"))));

		return new RequestPOJO(userID, RequestType.SEARCH_CLUB, params);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearch() {
		// Init
		FrontController fc = ProductionConfig.getFrontController();
		AnswerPOJO ans;
		// Build Request
		RequestPOJO rp = buildRP("id");
		// Do request to sv
		String id = fc.request(rp);
		ans = null;
		try {
			// Until answer is valid (not null)
			while (ans == null) {
				Thread.sleep(POLLING_DELAY);
				ans = fc.poll(id);
			}
		} catch (InterruptedException ie) {
			fail("Unknown interruption to main thread: " + ie.getMessage());
		}
		// Now test
		assertTrue("Valid operation unsucessful", (Boolean) ans.getAnswer()
				.get(0));

		List<ElementHelper<ClubPOJO>> results = (List<ElementHelper<ClubPOJO>>) ans
				.getAnswer().get(1);
		assertEquals("Unexpected answer size", 3, results.size());
		assertFalse("Wrong filtering on search at first element", results
				.get(0).isVisible());
		assertEquals("Unexpected ordering in answer, first element misplaced",
				results.get(0).getElement(), clubs.get(2));

		assertFalse("Wrong filtering on search at second element",
				results.get(1).isVisible());
		assertEquals("Unexpected ordering in answer, second element misplaced",
				results.get(1).getElement(), clubs.get(0));

		assertFalse("Wrong filtering on search at third element", results
				.get(2).isVisible());
		assertEquals("Unexpected ordering in answer, third element misplaced",
				results.get(2).getElement(), clubs.get(1));
	}
}
