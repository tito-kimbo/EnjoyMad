package es.ucm.fdi.business.requesthandling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.ProductionConfig;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.profilemanagement.ProfileManagerSA;
import es.ucm.fdi.business.requesthandling.tools.RequestType;
import es.ucm.fdi.business.util.ElementHelper;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

public class SearchClubHandlerTest {

	// The following lists are used for easy refactoring and test building
	private static List<ClubPOJO> clubs = Arrays.asList(
			new ClubPOJO("id1", "club1", "C/Falsa 1", 10.0f,
					new HashSet<TagPOJO>(Arrays.asList(new TagPOJO("pop")))),
			new ClubPOJO("id2", "club2", "C/Falsa 2", 15.0f,
					new HashSet<TagPOJO>(Arrays.asList(new TagPOJO("rock")))),
			new ClubPOJO("id3", "club3", "C/Falsa 3", 25.0f,
					new HashSet<TagPOJO>(Arrays.asList(new TagPOJO("pop"),
							new TagPOJO("rock"), new TagPOJO("hardstyle")))));

	private static List<UserPOJO> users = Arrays
			.asList(new UserPOJO("id1", "user1", "pass1", "user1@email.com",
					"Jose Pérez García", LocalDate.of(1990, Month.DECEMBER, 20)),
					new UserPOJO("id2", "user2", "pass2", "user2@email.com",
							"Jose Pérez García", LocalDate.of(1990,
									Month.DECEMBER, 20)),
					new UserPOJO("id3", "user3", "pass3", "user3@email.com",
							"Jose Pérez García", LocalDate.of(1990,
									Month.DECEMBER, 20)));

	private RequestPOJO buildRP(String userID, String pollID, String words,
			List<FilterPOJO> filters) {
		List<Object> data = new ArrayList<Object>();
		data.add(userID);
		data.add(words);
		data.add(filters);

		return new RequestPOJO(pollID, new RequestPOJO("", RequestType.SEARCH_CLUB,
				data));
	}

	private void fullInitialization() {
		Initializer.initialize();
		Initializer.addTags(Arrays.asList("rock", "pop", "hardstyle"));
	}

	private void addAll(List<ClubPOJO> clubs, List<UserPOJO> users,
			ProfileManagerSA pmsa) {
		for (ClubPOJO c : clubs) {
			pmsa.addNewClub(c);
		}

		for (UserPOJO user : users) {
			pmsa.addNewUser(user);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void singleClubSearch() {
		// Initialize
		fullInitialization();
		FrontController fc = Initializer.getFrontController();
		// Build and add user and club

		RequestPOJO rp = buildRP(users.get(0).getID(), users.get(0).getID(),
				"club", new ArrayList<FilterPOJO>());
		fc.getProfileManagerSA().addNewUser(users.get(0));
		fc.getProfileManagerSA().addNewClub(clubs.get(0));
		// Assign custom values to user
		Map<TagPOJO, Integer> vals = new HashMap<TagPOJO, Integer>();
		vals.put(new TagPOJO("pop"), 5);
		vals.put(new TagPOJO("rock"), 2);
		vals.put(new TagPOJO("hardstyle"), 1);

		UserPOJO user = fc.getProfileManagerSA().getUser(users.get(0).getID());
		user.setValueTags(vals);
		fc.getProfileManagerSA().removeUser(user.getID());
		fc.getProfileManagerSA().addNewUser(user);
		fc.getCustomDataSA().updateValues();

		// Send search request
		SearchClubHandler sch = new SearchClubHandler(fc, rp);

		// Test
		sch.run();
		AnswerPOJO answer = fc.poll(users.get(0).getID());
		assertTrue("Valid operation unsuccessful", (Boolean) answer.getAnswer()
				.get(0));
		List<ElementHelper<ClubPOJO>> searchResults = (List<ElementHelper<ClubPOJO>>) answer
				.getAnswer().get(1);
		assertTrue("List size different than expected", searchResults.size() == 1);
		assertEquals("Output club different from inserted", searchResults
				.get(0).getElement(), clubs.get(0));

		// Try some filtering
		List<FilterPOJO> filters = new ArrayList<FilterPOJO>();
		filters.add(new FilterPOJO(ProductionConfig.PRICE_FILTER, Arrays
				.asList("5.0")));

		rp = buildRP(users.get(0).getID(), users.get(0).getID(), "club",
				filters);
		sch = new SearchClubHandler(fc, rp);
		// Test
		sch.run();
		answer = fc.poll(users.get(0).getID());
		searchResults = (List<ElementHelper<ClubPOJO>>) answer.getAnswer().get(
				1);
		assertFalse("The price filter is not applied correctly", searchResults
				.get(0).isVisible());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void multiClubSearch() {
		fullInitialization();
		FrontController fc = Initializer.getFrontController();
		addAll(clubs, users, fc.getProfileManagerSA());	
		UserPOJO user;
		
		Map<TagPOJO, Integer> vals = new HashMap<TagPOJO, Integer>();
		vals.put(new TagPOJO("pop"), 5);
		vals.put(new TagPOJO("rock"), 2);
		vals.put(new TagPOJO("hardstyle"), 1);
		user = fc.getProfileManagerSA().getUser(users.get(0).getID());
		user.setValueTags(vals);
		fc.getProfileManagerSA().removeUser(user.getID());
		fc.getProfileManagerSA().addNewUser(user);

		vals = new HashMap<TagPOJO, Integer>();
		vals.put(new TagPOJO("pop"), 1);
		vals.put(new TagPOJO("rock"), 6);
		vals.put(new TagPOJO("hardstyle"), 0);
		user = fc.getProfileManagerSA().getUser(users.get(1).getID());
		user.setValueTags(vals);
		fc.getProfileManagerSA().removeUser(user.getID());
		fc.getProfileManagerSA().addNewUser(user);

		vals = new HashMap<TagPOJO, Integer>();
		vals.put(new TagPOJO("pop"), 1);
		vals.put(new TagPOJO("rock"), 6);
		vals.put(new TagPOJO("hardstyle"), 0);
		user = fc.getProfileManagerSA().getUser(users.get(2).getID());
		user.setValueTags(vals);
		fc.getProfileManagerSA().removeUser(user.getID());
		fc.getProfileManagerSA().addNewUser(user);
		// Assign custom values to users
		fc.getCustomDataSA().updateValues();
		
		// Actual search and testing
		RequestPOJO rp1, rp2, rp3;
		AnswerPOJO ans1, ans2, ans3;
		List<FilterPOJO> filters;
		SearchClubHandler handler;
		List<ElementHelper<ClubPOJO>> searchResults;

		filters = Arrays.asList(new FilterPOJO(ProductionConfig.TAG_FILTER,
				Arrays.asList("hardstyle")));
		rp1 = buildRP(users.get(0).getID(), users.get(0).getID(), "club", filters);
		handler = new SearchClubHandler(fc, rp1);
		handler.run();
		ans1 = fc.poll(users.get(0).getID());
		searchResults = (List<ElementHelper<ClubPOJO>>) ans1.getAnswer().get(1);

		assertTrue("Search operation not successful", (Boolean) ans1
				.getAnswer().get(0));
		assertTrue("Incorrect filtering operation or answer ordering", searchResults
				.get(0).isVisible());
		assertEquals(
				"Incorrect answer ordering for user 1 on multiclub search",
				clubs.get(0), searchResults.get(1).getElement());

		filters = new ArrayList<FilterPOJO>();
		rp2 = buildRP(users.get(1).getID(), users.get(1).getID(), "1", filters);
		handler = new SearchClubHandler(fc, rp2);
		handler.run();
		ans2 = fc.poll(users.get(1).getID());
		searchResults = (List<ElementHelper<ClubPOJO>>) ans2.getAnswer().get(1);
		assertTrue("Incorrect search by words for user 2 on multiclub search",
				searchResults.get(2).isVisible());
		assertEquals(
				"Incorrect answer ordering for user 2 on multiclub search",
				clubs.get(1), searchResults.get(1).getElement());

		rp3 = buildRP(users.get(2).getID(), users.get(2).getID(), "", filters);
		handler = new SearchClubHandler(fc, rp3);
		handler.run();
		ans3 = fc.poll(users.get(2).getID());
		searchResults = (List<ElementHelper<ClubPOJO>>) ans3.getAnswer().get(1);
		for (int i = 0; i < 3; ++i) {
			assertTrue("Search not performed correctly with empty words",
					searchResults.get(i).isVisible());
		}
	}

	public void concurrentMultiClubSearch() {
		fullInitialization();
	}
}
