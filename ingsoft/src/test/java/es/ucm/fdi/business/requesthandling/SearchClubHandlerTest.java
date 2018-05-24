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

		return new RequestPOJO(pollID, new RequestPOJO(RequestType.SEARCH_CLUB,
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
		fc.getProfileManagerSA().addNewUser(users.get(0));
		fc.getProfileManagerSA().addNewClub(clubs.get(0));

		RequestPOJO rp = buildRP(users.get(0).getID(), users.get(0).getID(),
				"club", new ArrayList<FilterPOJO>());
		// Assign custom values to user
		Map<TagPOJO, Integer> vals = new HashMap<TagPOJO, Integer>();
		vals.put(new TagPOJO("pop"), 5);
		vals.put(new TagPOJO("rock"), 2);
		vals.put(new TagPOJO("hardstyle"), 1);
		fc.getProfileManagerSA().getUser(users.get(0).getID())
				.setValueTags(vals);
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
		assertTrue("List size different than expected", searchResults.get(0)
				.isVisible());
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

	public void multiClubSearch() {
		fullInitialization();
		FrontController fc = Initializer.getFrontController();
		addAll(clubs, users, fc.getProfileManagerSA());

		// Assign custom values to users

		// Test
	}

	public void concurrentMultiClubSearch() {
		fullInitialization();
	}
}
