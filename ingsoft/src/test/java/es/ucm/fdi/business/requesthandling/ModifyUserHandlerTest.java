package es.ucm.fdi.business.requesthandling;

import org.junit.Test;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.ProductionConfig;
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
	private RequestPOJO buildModifyUserRP(UserPOJO user)
	{
		List<Object> l = new ArrayList<Object>();
		l.add(user);
		
		return new RequestPOJO(customID, new RequestPOJO(RequestType.MODIFY_USER, l));
	}
	
	@Test
	public void modifyUserTest() 
	{
		Initializer.initialize();
		FrontController fc = ProductionConfig.getFrontController();
		
		UserPOJO user = new UserPOJO("Id", "user", "password", "email@mail.com", "Francis", LocalDate.of(2018, Month.JANUARY, 1));
		
		fc.getProfileManagerSA().addNewUser(user);
		
		UserPOJO modifyUser = new UserPOJO(null, null, "0000", null, null, null);
		
		RequestPOJO rp = buildModifyUserRP(modifyUser);
		
		fc.request(rp);
	}
		
	
}
