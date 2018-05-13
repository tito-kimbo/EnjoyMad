package es.ucm.fdi.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * This class tests the functionality of ClubDAOImp.
 */

public class ClubDAOImpTest {
	private static int CONCURRENT_TESTS = 100;
	private static ClubPOJO club;
	private static ClubDAO clubDao;
	private static ArrayList<ClubPOJO> list = new ArrayList<ClubPOJO>();
	
	private static AssertionError assertionError;
	private static CountDownLatch latch; //Timer to allow multi-threading tests

	private static void createTestClubDAOImp() {
		club = new ClubPOJO("id", "Kapital", "Calle Atocha, 125, 28012 Madrid",
				17.0f, new HashSet<TagPOJO>(Arrays.asList(new TagPOJO(
						"Electronica"), new TagPOJO("Reggaeton"), new TagPOJO(
						"Funky"), new TagPOJO("R&B"))));

		clubDao = new ClubDAOImp();

		list = new ArrayList<ClubPOJO>(Arrays.asList(club));

		clubDao.addClub(club);
	}

	@Test
	public void testExist() {
		createTestClubDAOImp();
		assertEquals(clubDao.exists("id"), true);
	}

	@Test
	public void testGetClub() {
		createTestClubDAOImp();
		assertEquals(clubDao.getClub("id"), club);
	}

	@Test
	public void testGetClubs() {
		createTestClubDAOImp();
		assertEquals(clubDao.getClubs(), list);
	}

	@Test
	public void testRemoveClub() {
		createTestClubDAOImp();
		clubDao.removeClub("id");
		assertEquals(clubDao.exists("id"), false);
	}

	@Test
	synchronized public void concurrentReadTest() {
		//This is a timer that will make the program wait for the threads to execute
		latch = new CountDownLatch(CONCURRENT_TESTS);
		assertionError = null;
		
		createTestClubDAOImp();
		for (int i = 0; i < CONCURRENT_TESTS; ++i) {
			// Creates a new thread and runs it
			new Thread() {
				public void run() {
					try{
						assertEquals(
							"Concurrent reading is not thread safe for ClubDAOImp, "
									+ "mismatched club in DAO.",
							clubDao.getClub("id"), club);
						latch.countDown();
					}catch(AssertionError assError){
						assertionError = assError;
					}
				}
			}.start();
		}
		try{
			latch.await();
		}catch(InterruptedException ie){
			fail("Await interrupted, test could not finish properly.");
		}

		if(assertionError != null){
			fail(assertionError.getMessage());
		}
	}

	@Test
	synchronized public void concurrentWriteTest() {
		clubDao = new ClubDAOImp();
		club = new ClubPOJO("id", "Kapital", "Calle Atocha, 125, 28012 Madrid",
				17.0f, new HashSet<TagPOJO>(Arrays.asList(new TagPOJO(
						"Electronica"), new TagPOJO("Reggaeton"), new TagPOJO(
						"Funky"), new TagPOJO("R&B"))));

		for (int i = 0; i < CONCURRENT_TESTS; ++i) {
			new Thread() {
				public void run() {
					clubDao.addClub(club);
				}
				
				
			}.start();
		}
		assertEquals(
					"Concurrent writing is not thread safe for ClubDAOImp, "
							+ "mismatched club in DAO", clubDao.getClub("id"),
					club);
	}
}