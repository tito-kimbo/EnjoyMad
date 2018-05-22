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

public class TagDAOMySqlImpTest {

	private static int CONCURRENT_TESTS = 100;
	private static TagPOJO tag;
	private static TagDAO tagDao;
	private static List<TagPOJO> tagList;

	private static AssertionError assertionError;
	private static CountDownLatch latch; // Timer to allow multi-threading tests

	private static void createTestTagDAOImp() {

		tag = new TagPOJO("tag prueba");
		tagDao = new TagDAOMySqlImp();
		tagList = new ArrayList<TagPOJO>();

		tagList.add(new TagPOJO("buena musica"));
		tagList.add(new TagPOJO("copas gratis"));
		tagList.add(new TagPOJO("artistas famosos"));
		tagList.add(new TagPOJO("grande"));

	}

	@Test
	public void saveAndLoadTagTest() {

		createTestTagDAOImp();

		tagDao.saveTags(tagList);
		List<TagPOJO> lista1 = tagDao.loadTags();

		/*
		 * We must compare hash sets because TagPOJo hasnt internally order and
		 * the loadTags can return another order for the ArrayList
		 */

		assertEquals("error saving or loading events", 
				new HashSet<TagPOJO>(tagList),
				new HashSet<TagPOJO>(lista1)
				);

	}

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
					+ "mismatched tag in DAO.", 
					new HashSet<TagPOJO>(tagList), 
					new HashSet<TagPOJO>(tagDao.loadTags())
					);
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

		createTestTagDAOImp();
		tagDao.saveTags(tagList);
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
				tagDao.saveTags(tagList);
				latch.countDown();
			}

		}.start();
	}

	@Test
	public void concurrentWriteTest() {
		latch = new CountDownLatch(CONCURRENT_TESTS);

		createTestTagDAOImp();

		for (int i = 0; i < CONCURRENT_TESTS; ++i) {
			newWriteThread();
		}
		awaitForLatch();

		assertEquals("Concurrent writing is not thread safe for TagDAOImp, "
				+ "mismatched tag in DAO", new HashSet<TagPOJO>(tagList), 
				new HashSet<TagPOJO>(tagDao.loadTags())
				);
	}

	@Test
	public void concurrentReadWriteTest() {
		// This is a timer that will make the program wait for the threads to
		// execute
		latch = new CountDownLatch(2 * CONCURRENT_TESTS);
		assertionError = null;

		createTestTagDAOImp();
		tagDao.saveTags(tagList);
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

}