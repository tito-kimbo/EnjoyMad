package es.ucm.fdi.integration;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import es.ucm.fdi.integration.data.UserPOJO;

/**
 * This class tests the functionality of UserDAOImp.
 * @author Carlijn
 */

public class UserDAOImpTest{
	private static UserPOJO user;
	private static UserDAO userDao;
	private static ArrayList<UserPOJO> list = new ArrayList<UserPOJO>();
	
	private static void createTestUserDAOImp(){
		LocalDate date = LocalDate.of(1980, 1, 1);
		user = new UserPOJO("IDNumber1", "MyUser", "MyPsw", "myname@domain.com", "myname", date);
		
		userDao = new UserDAOImp();
		
		list = new ArrayList<UserPOJO>(Arrays.asList(user));
		
		userDao.addUser(user);
	}
	
	
	@Test
	public void testExist(){
		createTestUserDAOImp();
		assertEquals(userDao.exists("IDNumber1"),true);
	}

	@Test
	public void testGetUser() {
		createTestUserDAOImp();
		assertEquals(userDao.getUser("IDNumber1"), user);
	}
	
	@Test
	public void testGetUsers() {
		createTestUserDAOImp();
		assertEquals(userDao.getUsers(), list);
	}
	
	@Test
	public void testRemoveUser(){
		userDao.removeUser("IDNumber1");
		assertEquals(userDao.exists("IDNumber1"),false);
	}
}
