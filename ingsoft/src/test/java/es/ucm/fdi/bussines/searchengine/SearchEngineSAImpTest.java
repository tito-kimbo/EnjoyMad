package es.ucm.fdi.bussines.searchengine;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;
import es.ucm.fdi.business.searchengine.SearchEngineSAImp;
import es.ucm.fdi.business.util.ElementHelper;;

public class SearchEngineSAImpTest {
	private static ClubDAOImp cd;

	/**
	 * This initializes the ClubDao the first time the class is created.
	 */
	static {
		cd = new ClubDAOImp();

		Set<TagPOJO> l1 = new HashSet<TagPOJO>(); // Club's tags (empty)
		ClubPOJO c = new ClubPOJO("a2868521eac27ed8c7c4d7a38051f912", "Teatro Kapital",
				"C/Falsa 1234", 20.30F, l1);

		cd.addClub(c);
	}

	/**
	 * This method returns an user ready to execute searchs.
	 */
	public static UserPOJO readyToSearchUser(String id, String user, String name) {
		UserPOJO usr = new UserPOJO(id, user, "password", "email@mail.com", name,
				LocalDate.of(2018, Month.JANUARY, 1));
		usr.setPreferencesList(cd.getClubs());

		return usr;
	}

	/**
	 * TEST 1: Search without filters.
	 */
	@Test
	public void noFilterSearchTest() {
		List<FilterPOJO> filters = new ArrayList<FilterPOJO>(); // Filters
																// (empty)
		UserPOJO usr = readyToSearchUser("id", "frblazqu", "Francis");

		//List<ElementHelper<ClubPOJO>> aux = search("", filters, usr);
	}
}
