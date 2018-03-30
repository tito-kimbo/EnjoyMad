package es.ucm.fdi;

import static org.junit.Assert.*;
import org.junit.Test;

import es.ucm.fdi.integration.data.UserPOJO;

/**
 *This test execute several cases of the userPOJO.
 *Should return Success, Failure, Failure, Failure, Failure, Failure in that order.
 *@author Carlijn
 */

public class TestUserPOJO {
	
	/**
	 * Tests a normal case of user info.
	 * @result account will be created without issues.
	 */
	
	@Test
	public final void testCorrectAnswers() {
		UserPOJO user = new UserPOJO("IDNumber1","MyPsw","myname@domain.com","myname",1,1,1980);
		assertEquals(user.getBirthday().getDay(),1);
		assertEquals(user.getBirthday().getMonth(),1);
		assertEquals(user.getBirthday().getYear(),1980);
		assertEquals(user.getEmail(),"myname@domain.com");
		assertEquals(user.getID(),"IDNumber1");
		assertEquals(user.getName(),"myname");
		assertEquals(user.getPassword(),"MyPsw");
	}
	
	/**
	 * Tests a younger user than we want (minimum year of birth 1999).
	 * @result incorrect year and thus account won't be created properly.
	 */
	
	@Test
	public final void testIncorrectYear() {
		UserPOJO user = new UserPOJO("IDNumber1","MyPsw","myname@domain.com","myname",1,1,2010);
		assertEquals(user.getBirthday().getYear(),2010);
	}
	
	/**
	 * Tests a month out of range.
	 * @result incorrect month and thus account won't be created properly.
	 */
	
	@Test
	public final void testIncorrectMonth() {
		UserPOJO user = new UserPOJO("IDNumber1","MyPsw","myname@domain.com","myname",1,25,1980);
		assertEquals(user.getBirthday().getMonth(),25);
	}
	
	/**
	 * Tests a day out of range.
	 * @result incorrect day and thus account won't be created properly.
	 */
	
	@Test
	public final void testIncorrectDay() {
		UserPOJO user = new UserPOJO("IDNumber1","MyPsw","myname@domain.com","myname",32,1,1980);
		assertEquals(user.getBirthday().getDay(),32);
	}
	
	/**
	 * Tests an invalid email address.
	 * @result incorrect email and thus account won't be created properly.
	 */
	
	@Test
	public final void testIncorrectEmail() {
		UserPOJO user = new UserPOJO("IDNumber1","MyPsw","mynamedomain.com","myname",1,1,1980);
		assertEquals(user.getEmail(),"mynamedomain.com");
	}
	
	/**
	 * Tests empty strings.
	 * @result empty strings and thus account won't be created properly.
	 */
	
	@Test
	public final void testEmptyStrings() {
		UserPOJO user = new UserPOJO("","","","",1,1,1980);
		assertEquals(user.getEmail(),"");
		assertEquals(user.getID(),"");
		assertEquals(user.getName(),"");
		assertEquals(user.getPassword(),"");
	}

}
