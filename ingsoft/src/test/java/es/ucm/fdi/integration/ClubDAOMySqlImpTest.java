package es.ucm.fdi.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * This class tests the functionality of ClubDAOImp.
 */

public class ClubDAOMySqlImpTest {
	private static int CONCURRENT_TESTS = 10;
	private static ClubPOJO club;
	private static ClubDAO clubDao;
	private static ArrayList<ClubPOJO> list = new ArrayList<ClubPOJO>();

	private static AssertionError assertionError;
	private static CountDownLatch latch; // Timer to allow multi-threading tests

	private static void createTestClubDAOMySqlImp() {
		Set<TagPOJO> tags = new HashSet<TagPOJO>();
		
		tags.add(new TagPOJO("Electronica"));
		tags.add(new TagPOJO("Reggaeton"));
		tags.add(new TagPOJO("Funky"));
		tags.add(new TagPOJO("R&B"));
		
		club = new ClubPOJO("id", "Kapital", "Calle Atocha, 125, 28012 Madrid",
				17.0f, tags);
		
		
		clubDao = new ClubDAOMySqlImp();
		clubDao.removeClub("id");
		
		list = new ArrayList<ClubPOJO>(Arrays.asList(club));
	}

	@Test
	public void testExist() {
		createTestClubDAOMySqlImp();
		clubDao.addClub(club);
		assertEquals(clubDao.exists("id"), true);
		clubDao.removeClub("id");
	}

	@Test
	public void testGetClub() {
		createTestClubDAOMySqlImp();
		clubDao.addClub(club);
		ClubPOJO otherClub = clubDao.getClub("id");
		assertEquals(club, otherClub);
		clubDao.removeClub("id");
	}

	@Test
	public void testGetClubs() {
		createTestClubDAOMySqlImp();
		clubDao.addClub(club);
		
		List<ClubPOJO> otherlist = clubDao.getClubs();
		assertEquals(list, otherlist);
		
		for(ClubPOJO club : list)
			clubDao.removeClub(club.getID());
	}

	@Test
	public void testRemoveClub() {
		createTestClubDAOMySqlImp();
		clubDao.addClub(club);
		clubDao.removeClub("id");
		assertEquals(clubDao.exists("id"), false);
		clubDao.removeClub(club.getID());
	}
	private void awaitForLatch() {
		try {
			latch.await();
		} catch (InterruptedException ie) {
			fail("Await interrupted, test could not finish properly.");
		}

	}
	//@Test
	public void concurrentReadTest() {
		// This is a timer that will make the program wait for the threads to
		// execute
		latch = new CountDownLatch(CONCURRENT_TESTS);
		assertionError = null;

		createTestClubDAOMySqlImp();
		for (int i = 0; i < CONCURRENT_TESTS; ++i) {
			// Creates a new thread and runs it
			newReadThread();
		}
		awaitForLatch();

		if (assertionError != null) {
			fail(assertionError.getMessage());
		}
	}

	private void newWriteThread() {
		// Write thread
		new Thread() {
			public void run() {
				clubDao.addClub(club);
				latch.countDown();
			}

		}.start();
	}

	@Test
	public void concurrentWriteTest() {
		latch = new CountDownLatch(CONCURRENT_TESTS);
		clubDao = new ClubDAOImp();
		club = new ClubPOJO("id", "Kapital", "Calle Atocha, 125, 28012 Madrid",
				17.0f, new HashSet<TagPOJO>(Arrays.asList(new TagPOJO(
						"Electronica"), new TagPOJO("Reggaeton"), new TagPOJO(
						"Funky"), new TagPOJO("R&B"))));

		for (int i = 0; i < CONCURRENT_TESTS; ++i) {
			newWriteThread();
		}
		awaitForLatch();

		assertEquals("Concurrent writing is not thread safe for ClubDAOImp, "
				+ "mismatched club in DAO", clubDao.getClub("id"), club);
	}

	private void newReadThread() {
		new Thread() {
			public void run() {
				try {
					assertEquals(
							"Concurrent reading is not thread safe for ClubDAOImp, "
									+ "mismatched club in DAO.",
							clubDao.getClub("id"), club);
				} catch (AssertionError assError) {
					assertionError = assError;
				} finally {
					latch.countDown();
				}
			}
		}.start();
	}
	/*
	@Test
	public void concurrentReadWriteTest() {
		// This is a timer that will make the program wait for the threads to
		// execute
		latch = new CountDownLatch(2 * CONCURRENT_TESTS);
		assertionError = null;
		club = new ClubPOJO("id", "Kapital", "Calle Atocha, 125, 28012 Madrid",
				17.0f, new HashSet<TagPOJO>(Arrays.asList(new TagPOJO(
						"Electronica"), new TagPOJO("Reggaeton"), new TagPOJO(
						"Funky"), new TagPOJO("R&B"))));

		clubDao.addClub(club);
		for (int i = 0; i < CONCURRENT_TESTS; ++i) {
			// Read thread
			newReadThread();

			// Write thread
			newWriteThread();
		}
		awaitForLatch();

		if (assertionError != null) {
			fail("Read/write interaction is not thread safe in ClubDAOImp.\n"
					+ assertionError.getMessage());
		}
	}
	*/
}