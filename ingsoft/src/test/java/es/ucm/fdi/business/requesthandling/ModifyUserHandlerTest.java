package es.ucm.fdi.business.requesthandling;

import org.junit.Test;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.ProductionConfig;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.requesthandling.tools.RequestType;
import es.ucm.fdi.integration.data.UserPOJO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ModifyUserHandlerTest {
	
	private static String customID = "idModify User240520189229";
	private static int THREAD_LIMIT = 100;

	// Uses customID
	private RequestPOJO buildModifyUserRP(String userId, UserPOJO user)
	{
		List<Object> l = new ArrayList<Object>();
		l.add(userId);
		l.add(user);
		
		return new RequestPOJO(customID, new RequestPOJO(RequestType.MODIFY_USER, l));
	}
	
	/**
	 * Test to prove that the way to modify users using request works properly.
	 */
	@Test
	public void modifyUserTest() 
	{
		//We initialize the FrontController
		Initializer.initialize();
		FrontController fc = ProductionConfig.getFrontController();
		
		//The system should have at least one user to modify it, we add the user
		UserPOJO user = new UserPOJO("Id", "user", "password", "email@mail.com", 
				"Francis Blázquez", LocalDate.of(1998, Month.JANUARY, 1));
		
		fc.getProfileManagerSA().addNewUser(user);
		
		//Then we create the RequestPOJO to ask the system to modify the user
		UserPOJO modifyUser = new UserPOJO("Id", "titoKimbo", null, null, null, null);
		
		RequestPOJO rp = buildModifyUserRP(modifyUser.getID(), modifyUser);
		
		ModifyUserHandler handler = new ModifyUserHandler(fc, rp);
		
		//We execute the request
		handler.run();
		
		//We check that the system processed the request and did it successfully.
		assertTrue("Modifying the user failed.", (Boolean) fc.poll(customID).getAnswer().get(0));
		assertEquals("Modifying the user failed.", 
				(String) fc.getProfileManagerSA().getUser("Id").getUsername(), "titoKimbo");
	}
		
	
}
