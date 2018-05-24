package es.ucm.fdi.business.requesthandling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.ProductionConfig;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.requesthandling.tools.RequestType;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.TagPOJO;

public class RegisterClubHandlerTest {
	private static String customID = "idRegister Club240520189229";
	private static int THREAD_LIMIT = 100;

	// Uses customID
	private RequestPOJO buildOneClubRP(ClubPOJO club) {
		List<Object> l = new ArrayList<Object>();
		l.add(club);
		return new RequestPOJO(customID, new RequestPOJO(
				RequestType.REGISTER_CLUB, l));
	}

	@Test
	public void validUserTest() {
		Initializator.initialize();
		FrontController fc = ProductionConfig.getFrontController();
		ClubPOJO club = new ClubPOJO("id", "Disco", "C/Falsa, 123", 10.0f,
				new HashSet<TagPOJO>());
		RegisterClubHandler rch = new RegisterClubHandler(fc,
				buildOneClubRP(club));

		rch.run();
		assertTrue("Adding new club was not successful",
				(Boolean) fc.poll(customID).getAnswer().get(0));
		assertEquals("Club data wrongfully inserted", club, Initializator
				.getClubDAO().getClub("id"));
	}

	@Test
	public void invalidUserTest() {
		Initializator.initialize();
		FrontController fc = ProductionConfig.getFrontController();

		// INVALID ID
		ClubPOJO club = new ClubPOJO("%%", "Disco", "C/Falsa, 123", 10.0f,
				new HashSet<TagPOJO>());
		;
		RegisterClubHandler rch = new RegisterClubHandler(fc,
				buildOneClubRP(club));
		rch.run();
		assertFalse("Successful operation with illegal ID",
				(Boolean) fc.poll(customID).getAnswer().get(0));

		// INVALID PRICE
		club = new ClubPOJO("id", "Disco", "C/Falsa, 123", -10.0f,
				new HashSet<TagPOJO>());
		rch = new RegisterClubHandler(fc, buildOneClubRP(club));
		rch.run();
		assertFalse("Successful operation with illegal price", (Boolean) fc
				.poll(customID).getAnswer().get(0));

		// INVALID TAGS
		Set<TagPOJO> set = new HashSet<TagPOJO>();
		set.add(new TagPOJO("&&"));
		club = new ClubPOJO("id", "Disco", "C/Falsa, 123", 10.0f, set);
		rch = new RegisterClubHandler(fc, buildOneClubRP(club));
		rch.run();
		assertFalse("Successful operation with illegal tags", (Boolean) fc
				.poll(customID).getAnswer().get(0));

	}

	@Test
	public void concurrentTest() {
		Initializator.initialize();
		FrontController fc = ProductionConfig.getFrontController();
		ClubPOJO club;
		int estimatedDelay = 100; // Estimated time it will take for the op in
									// ms

		// News are used to avoid problems with references
		for (int i = 0; i < THREAD_LIMIT; ++i) {
			club = new ClubPOJO("id" + i, "Disco", "C/Falsa, 123", 10.0f,
					new HashSet<TagPOJO>());

			new Thread(new RegisterClubHandler(fc, buildOneClubRP(club)))
					.start();
		}

		try {
			Thread.sleep(estimatedDelay);
			for (int i = 0; i < THREAD_LIMIT; ++i) {
				club = new ClubPOJO("id" + i, "Disco", "C/Falsa, 123", 10.0f,
						new HashSet<TagPOJO>());
				ClubPOJO c = Initializator.getClubDAO().getClub("id" + i);
				boolean b = club.equals(c);
				if(!b)
					System.err.print("");
				assertEquals("User number " + i + " not inserted properly", club, c);
			}

		} catch (InterruptedException ie) {
			fail("Unknown test thread interruption: " + ie.getMessage());
		}
	}
}
