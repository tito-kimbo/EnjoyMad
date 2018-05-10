package es.ucm.fdi.integration;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import es.ucm.fdi.integration.data.UserPOJO;

/**
 * This class tests the functionality of UserDAOImp.
 */
public class UserDAOImpTest{
	private static UserPOJO user;
	private static UserDAO userDAO;
	private static ArrayList<UserPOJO> list = new ArrayList<UserPOJO>();
	
	private static void createTestUserDAOImp(){
		LocalDate date = LocalDate.of(1980, 1, 1);
		user = new UserPOJO("IDNumber1", "MyUser", "MyPsw", "myname@domain.com", "myname", date);
		
		userDAO = new UserDAOImp();
		
		list = new ArrayList<UserPOJO>(Arrays.asList(user));
		
		userDAO.addUser(user);
	}
	
	
	@Test
	public void testExist(){
		createTestUserDAOImp();
		assertTrue("Error: User not properly added to UserDAO", userDAO.exists("IDNumber1"));
	}

	@Test
	public void testGetUser() {
		createTestUserDAOImp();
		assertEquals("Error: user data not properly transferred by UserDAO", userDAO.getUser("IDNumber1"), user);
	}
	
	@Test
	public void testGetUsers() {
		createTestUserDAOImp();
		assertEquals("Error: users added incorrectly to UserDAO", userDAO.getUsers(), list);
	}
	
	@Test
	public void testRemoveUser(){
		userDAO.removeUser("IDNumber1");
		assertFalse("Error: indicated user should not exist in UserDAO.", userDAO.exists("IDNumber1"));
	}
}
