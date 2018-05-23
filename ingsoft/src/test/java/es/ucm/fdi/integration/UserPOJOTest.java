package es.ucm.fdi.integration;

import java.time.Month;
import java.time.LocalDate;
import java.time.DateTimeException;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import es.ucm.fdi.integration.data.UserPOJO;

/**
 *This test execute several cases of the <code>UserPOJO</code>.
 *Should return Success, Failure, Failure, Failure, Failure, Failure in that order.
 */

public class UserPOJOTest {
	
	/**
	 * Tests a normal case of user info.
	 * @result account will be created without issues.
	 */
	//
	@Test
	public final void testCorrectAnswers() {
		LocalDate date = LocalDate.of(1980, 1, 1);
		UserPOJO user = new UserPOJO("IDNumber1", "MyUser", "MyPsw", "myname@domain.com", "myname", date);
		assertEquals(user.getBirthday().getDayOfMonth(),1);
		assertEquals(user.getBirthday().getMonth(), Month.JANUARY);
		assertEquals(user.getBirthday().getYear(),1980);
		assertEquals(user.getEmail(),"myname@domain.com");
		assertEquals(user.getID(),"IDNumber1");
		assertEquals(user.getName(),"myname");
		assertEquals(user.getUsername(), "MyUser");
		assertEquals(user.getHashedPassword(),"MyPsw");
	}
	
	/**
	 * Tests for younger users than we want.
	 * @result incorrect year and thus account won't be created properly.
	 */
	
	@Test
	public final void testIncorrectYear() {
		LocalDate date = LocalDate.of(2010, 1, 1);
		UserPOJO user = new UserPOJO("IDNumber1", "MyUser", "MyPsw", "mynamedomain.com", "myname", date);
		assertEquals(user.getBirthday().getYear(), 2010);
	}
	
	//WE WILL ASSUME THE MONTH IS IN RANGE (SHOULD BE CONTROLLED IN PRESENTATION LAYER)
	/**
	 * Tests a day out of range.
	 * @result incorrect day and thus account won't be created properly.
	 */
	@Test(expected = DateTimeException.class)
	public final void testIncorrectDay() {
		LocalDate date = LocalDate.of(1980, 32, 1);		
		new UserPOJO("IDNumber1", "MyUser", "MyPsw", "myname@domain.com", "myname", date);
	}

}
