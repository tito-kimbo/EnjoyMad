package es.ucm.fdi.integration;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import es.ucm.fdi.integration.data.SessionPOJO;

public class SessionDAOMySqlImpTest {

	private static int CONCURRENT_TESTS = 100;
	private static SessionDAO sessionDao;

	private static Set<SessionPOJO> sessionsSet;

	private static AssertionError assertionError;
	private static CountDownLatch latch; // Timer to allow multi-threading tests

	static{
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
	}
	
	private static void createTestSessionDAOImp() {

		sessionDao = new SessionDAOMySqlImp();
		sessionsSet = new HashSet<SessionPOJO>();

		SessionPOJO sPOJO1 = new SessionPOJO("session 1", LocalDateTime.of(
				2018, 5, 15, 16, 56, 0));
		SessionPOJO sPOJO2 = new SessionPOJO("session 2", LocalDateTime.of(
				2018, 5, 15, 16, 55, 0));
		SessionPOJO sPOJO3 = new SessionPOJO("session 3", LocalDateTime.of(
				2018, 5, 15, 16, 57, 0));
		SessionPOJO sPOJO4 = new SessionPOJO("session 4", LocalDateTime.of(
				2018, 5, 15, 16, 57, 0));
		
		
		sessionsSet.add(sPOJO1);
		sessionsSet.add(sPOJO2);
		sessionsSet.add(sPOJO3);
		sessionsSet.add(sPOJO4);
		
		
		for(SessionPOJO s : sessionsSet)
			sessionDao.removeSession(s.getID());
		
		for (SessionPOJO sp : sessionsSet) 
			sessionDao.addSession(sp);
	}
	/**
	 * All functionality of the DAO is in this Test because actually is very
	 * simple
	 */

	@Test
	public void addRemoveAndExistsTest() {

		createTestSessionDAOImp();

		assertTrue("session 1 must be included in the Session DAO",
				sessionDao.exist("session 1"));
		assertFalse("session 5 was not added previosly",
				sessionDao.exist("session 5"));

	}

	/**
	 * Checking that returns a correct list with all the sessions active We
	 * compare that with a HashMap because the order can be different It doesn't
	 * care
	 */

	@Test
	public void listOfSessionsTest() {
		createTestSessionDAOImp();
		Set<SessionPOJO> set = new HashSet<SessionPOJO>(sessionDao.getSessions());
		
		int i = 0;
		for(SessionPOJO sx : set)
			for(SessionPOJO sy : sessionsSet)
				if(sx.equals(sy)) {
					i++;
					break;
				}
		
		assertEquals("The DAO has not the correct sessions", set.size(), i);
	}

	/**
	 * Now we want to know if removes correctly
	 */

	@Test
	public void removeSessionTest() {

		createTestSessionDAOImp();

		assertTrue("session 1 must be added", sessionDao.exist("session 1"));

		sessionDao.removeSession("session 1");

		assertFalse("session 1 must be removed but it is not",
				sessionDao.exist("session 1"));
	}
	/*
	private void awaitForLatch() {
		try {
			latch.await();
		} catch (InterruptedException ie) {
			fail("Await interrupted, test could not finish properly.");
		}

	}

	private void newReadThread() {
		new Thread() {
			public void run() {
				try {
					assertEquals(
							"Concurrent reading is not thread safe for TagDAOImp, "
									+ "mismatched tag in DAO.", sessionsSet,
							new HashSet<SessionPOJO>(sessionDao.getSessions()));
				} catch (AssertionError assError) {
					assertionError = assError;
				} finally {
					latch.countDown();
				}
			}
		}.start();
	}

	@Test
	public void concurrentReadTest() {

		// This is a timer that will make the program wait for the threads to
		// execute

		latch = new CountDownLatch(CONCURRENT_TESTS);
		assertionError = null;

		createTestSessionDAOImp();

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
				writeInDAO();
				latch.countDown();
			}

		}.start();
	}

	@Test
	public void concurrentWriteTest() {
		latch = new CountDownLatch(CONCURRENT_TESTS);

		createTestSessionDAOImp();

		for (int i = 0; i < CONCURRENT_TESTS; ++i) {
			newWriteThread();
		}
		awaitForLatch();

		assertEquals("Concurrent writing is not thread safe for TagDAOImp, "
				+ "mismatched tag in DAO", sessionsSet,
				new HashSet<SessionPOJO>(sessionDao.getSessions()));
	}

	@Test
	public void concurrentReadWriteTest() {
		// This is a timer that will make the program wait for the threads to
		// execute
		latch = new CountDownLatch(2 * CONCURRENT_TESTS);
		assertionError = null;

		createTestSessionDAOImp();

		for (int i = 0; i < CONCURRENT_TESTS; ++i) {
			// Read thread
			newReadThread();

			// Write thread
			newWriteThread();
		}
		awaitForLatch();

		if (assertionError != null) {
			fail("Read/write interaction is not thread safe in TagDAOImp.\n"
					+ assertionError.getMessage());
		}
	}
	*/
}
