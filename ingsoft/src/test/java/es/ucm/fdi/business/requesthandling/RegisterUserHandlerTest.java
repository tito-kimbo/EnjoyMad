package es.ucm.fdi.business.requesthandling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.ProductionConfig;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.requesthandling.Initializator;
import es.ucm.fdi.business.requesthandling.tools.RequestType;
import es.ucm.fdi.integration.data.UserPOJO;

public class RegisterUserHandlerTest {
	private static String customID = "idRegister User240520189229";
	private static int THREAD_LIMIT = 100;

	// Uses customID
	private RequestPOJO buildOneUserRP(UserPOJO user) {
		List<Object> l = new ArrayList<Object>();
		l.add(user);
		return new RequestPOJO(customID, new RequestPOJO(
				RequestType.REGISTER_USER, l));
	}

	@Test
	public void validUserTest() {
		Initializator.initialize();
		FrontController fc = ProductionConfig.getFrontController();
		UserPOJO user = new UserPOJO("id", "user", "1234", "user@gmail.com",
				"Manuel García Pérez", LocalDate.of(1996, Month.AUGUST, 13));
		RegisterUserHandler ruh = new RegisterUserHandler(fc,
				buildOneUserRP(user));

		ruh.run();
		assertTrue("Adding new user was not successful",
				(Boolean) fc.poll(customID).getAnswer().get(0));
		assertEquals("User data wrongfully inserted", user, Initializator
				.getUserDAO().getUser("id"));
	}

	@Test
	public void invalidUserTest() {
		Initializator.initialize();
		FrontController fc = ProductionConfig.getFrontController();

		// INVALID ID
		UserPOJO user = new UserPOJO("%%", "user", "1234", "user@gmail.com",
				"Manuel García Pérez", LocalDate.of(1996, Month.AUGUST, 13));
		RegisterUserHandler ruh = new RegisterUserHandler(fc,
				buildOneUserRP(user));
		ruh.run();
		assertFalse("Successful operation with illegal ID",
				(Boolean) fc.poll(customID).getAnswer().get(0));

		// INVALID USERNAME
		user = new UserPOJO("id", "%%", "1234", "user@gmail.com",
				"Manuel García Pérez", LocalDate.of(1996, Month.AUGUST, 13));
		ruh = new RegisterUserHandler(fc, buildOneUserRP(user));
		ruh.run();
		assertFalse("Successful operation with illegal username", (Boolean) fc
				.poll(customID).getAnswer().get(0));

		// INVALID EMAIL
		user = new UserPOJO("id", "user", "1234", "usergmail.com",
				"Manuel García Pérez", LocalDate.of(1996, Month.AUGUST, 13));
		ruh = new RegisterUserHandler(fc, buildOneUserRP(user));
		ruh.run();
		assertFalse("Successful operation with illegal email", (Boolean) fc
				.poll(customID).getAnswer().get(0));

		// INVALID NAME
		user = new UserPOJO("id", "user", "1234", "user@gmail.com", "%???",
				LocalDate.of(1996, Month.AUGUST, 13));
		ruh = new RegisterUserHandler(fc, buildOneUserRP(user));
		ruh.run();
		assertFalse("Successful operation with illegal name", (Boolean) fc
				.poll(customID).getAnswer().get(0));

		// INVALID YEAR
		user = new UserPOJO("id", "user", "1234", "user@gmail.com",
				"Manuel García Pérez", LocalDate.of(2008, Month.AUGUST, 13));
		ruh = new RegisterUserHandler(fc, buildOneUserRP(user));
		ruh.run();
		assertFalse("Successful operation with illegal birthday", (Boolean) fc
				.poll(customID).getAnswer().get(0));

	}

	@Test
	public void concurrentTest() {
		Initializator.initialize();
		FrontController fc = ProductionConfig.getFrontController();
		UserPOJO user;
		int estimatedDelay = 100; //Estimated time it will take for the op in ms

		//News are used to avoid problems with references
		for (int i = 0; i < THREAD_LIMIT; ++i) {
			user = new UserPOJO("id" + i, "user", "Password123/",
					"user@gmail.com", "María Fernández Gómez", LocalDate.of(1990, Month.JANUARY,
							6));

			new Thread(new RegisterUserHandler(fc, buildOneUserRP(user)))
					.start();
		}
		
		try {
			Thread.sleep(estimatedDelay);
			for (int i = 0; i < THREAD_LIMIT; ++i) {
				user = new UserPOJO("id" + i, "user", "Password123/",
						"user@gmail.com", "María Fernández Gómez", LocalDate.of(1990,
								Month.JANUARY, 6));
				assertEquals(i + "th user not inserted properly", user,
						Initializator.getUserDAO().getUser("id" + i));
			}

		} catch (InterruptedException ie) {
			fail("Unknown test thread interruption: " + ie.getMessage());
		}
	}
}
