package es.ucm.fdi.integration;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * This class tests the functionality of UserDAOImp.
 */
public class UserDAOImpTest {
	private static int CONCURRENT_TESTS = 100;
	private static UserPOJO user;
	private static UserDAO userDao;
	private static ArrayList<UserPOJO> list = new ArrayList<UserPOJO>();

	private static AssertionError assertionError;
	private static CountDownLatch latch; // Timer to allow multi-threading tests

	private static void createTestUserDAOImp() {
		LocalDate date = LocalDate.of(1980, 1, 1);
		user = new UserPOJO("IDNumber1", "MyUser", "MyPsw",
				"myname@domain.com", "myname", date);

		userDao = new UserDAOImp();

		list = new ArrayList<UserPOJO>(Arrays.asList(user));

		userDao.addUser(user);
	}

	@Test
	public void testExist() {
		createTestUserDAOImp();
		assertTrue("Error: User not properly added to UserDAO",
				userDao.exists("IDNumber1"));
	}

	@Test
	public void testGetUser() {
		createTestUserDAOImp();
		assertEquals("Error: user data not properly transferred by UserDAO",
				userDao.getUser("IDNumber1"), user);
	}

	@Test
	public void testGetUsers() {
		createTestUserDAOImp();
		assertEquals("Error: users added incorrectly to UserDAO",
				userDao.getUsers(), list);
	}

	@Test
	public void testRemoveUser() {
		userDao.removeUser("IDNumber1");
		assertFalse("Error: indicated user should not exist in UserDAO.",
				userDao.exists("IDNumber1"));
	}

	@Test
	public void concurrentReadTest() {
		// This is a timer that will make the program wait for the threads to
		// execute
		latch = new CountDownLatch(CONCURRENT_TESTS);
		assertionError = null;

		createTestUserDAOImp();
		for (int i = 0; i < CONCURRENT_TESTS; ++i) {
			
			// Creates a new thread and runs it
			newReadThread();
			
		}
		awaitForLatch();

		/*if (assertionError != null) {
			fail(assertionError.getMessage());
		}*/
	}

	private void newWriteThread() {
		// Write thread
		new Thread() {
			public void run() {
				userDao.addUser(user);
				latch.countDown();
			}
			

		}.start();
	}

	private void newReadThread() {
		new Thread() {
			public void run() {
				try {
					assertEquals(
							"Concurrent reading is not thread safe for UserDAOImp, "
									+ "mismatched user in DAO.",
							userDao.getUser("id"), user);
					
				} catch (AssertionError assError) {
					assertionError = assError;
				} finally {
					latch.countDown();
				}
			}
		}.start();
		
	}

	private void awaitForLatch() {
		try {
			latch.await();
		} catch (InterruptedException ie) {
			fail("Await interrupted, test could not finish properly.");
		}
	}

	@Test
	public void concurrentWriteTest() {
		latch = new CountDownLatch(CONCURRENT_TESTS);
		userDao = new UserDAOImp();
		user = new UserPOJO("id", "Juan13", "constrasena", "juan23@gmail.com",
				"Juan García", LocalDate.of(2018, 5, 13));

		for (int i = 0; i < CONCURRENT_TESTS; ++i) {
			newWriteThread();
		}
		awaitForLatch();
		
		assertEquals("Concurrent writing is not thread safe for ClubDAOImp, "
				+ "mismatched club in DAO", 
				userDao.getUser("id"), user);
	}
	
	@Test
	public void concurrentReadWriteTest(){
		//This is a timer that will make the program wait for the threads to execute
		latch = new CountDownLatch(2*CONCURRENT_TESTS);
		assertionError = null;
		user = new UserPOJO("id", "Juan13", "constrasena", "juan23@gmail.com",
				"Juan García", LocalDate.now());
		
		
		userDao.addUser(user);
		for(int i = 0; i < CONCURRENT_TESTS; ++i){	
			
			//Read thread
			newReadThread();
			
			//Write thread
			newWriteThread();
		}
		awaitForLatch();
		
		if(assertionError != null){
			fail("Read/write interaction is not thread safe in ClubDAOImp.\n" 
					+assertionError.getMessage());
		}
	}

}