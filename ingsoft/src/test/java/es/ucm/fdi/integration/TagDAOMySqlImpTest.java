package es.ucm.fdi.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import es.ucm.fdi.integration.data.TagPOJO;

/**
 * This class tests the functionality of ClubDAOImp.
 */

public class TagDAOMySqlImpTest {

	private static int CONCURRENT_TESTS = 10; // Low to avoid max connection
												// errors, at least temporally
	private static TagDAO tagDao;
	private static List<TagPOJO> tagList;

	private static AssertionError assertionError;
	private static CountDownLatch latch; // Timer to allow multi-threading tests

	static {
		// Initialization for tagList
		tagList = new ArrayList<TagPOJO>();
		tagList.add(new TagPOJO("electronica"));
		tagList.add(new TagPOJO("pop"));
		tagList.add(new TagPOJO("reggaeton"));
	}

	private static void createTestTagDAOImp() {
		tagDao = new TagDAOMySqlImp();
		tagList = new ArrayList<TagPOJO>();

		tagList.add(new TagPOJO("buena musica"));
		tagList.add(new TagPOJO("copas gratis"));
		tagList.add(new TagPOJO("artistas famosos"));
		tagList.add(new TagPOJO("grande"));

		tagDao.saveTags(new ArrayList<TagPOJO>());
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

		assertEquals("error saving or loading events", new HashSet<TagPOJO>(
				tagList), new HashSet<TagPOJO>(lista1));

	}

	private void awaitForLatch() {
		try {
			latch.await();
		} catch (InterruptedException ie) {
			fail("Await interrupted, test could not finish properly.");
		}

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
				new HashSet<TagPOJO>(tagDao.loadTags()));
	}

	//@Test
	public void concurrentReadWriteTest() {
		// This is a timer that will make the program wait for the threads to
		// execute
		latch = new CountDownLatch(CONCURRENT_TESTS / 2);
		assertionError = null;

		createTestTagDAOImp();
		tagDao.saveTags(tagList);
		for (int i = 0; i < CONCURRENT_TESTS / 2; ++i) {
			// Write thread
			newWriteThread();
			// Read thread
			newReadThread();
		}
		awaitForLatch();

		if (assertionError != null) {
			fail("Read/write interaction is not thread safe in TagDAOImp.\n"
					+ assertionError.getMessage());
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
	private void newReadThread() {
		new Thread() {
			public void run() {
				try {
					HashSet<TagPOJO> set = new HashSet<TagPOJO>(tagDao.loadTags());
					//boolean b=set.equals(new HashSet<TagPOJO>(tagList));
					//if(!b)
					//	System.err.println("Shouldnt happen");
					assertEquals(
							"Concurrent reading is not thread safe for TagDAOImp, "
									+ "mismatched tag in DAO.",
							new HashSet<TagPOJO>(tagList),set);
				} catch (AssertionError assError) {
					assertionError = assError;
				} finally {
					latch.countDown();
				}
			}
		}.start();
	}
}