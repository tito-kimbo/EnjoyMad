package es.ucm.fdi.business.requesthandling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.ProductionConfig;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.requesthandling.tools.RequestType;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.TagPOJO;

public class ModifyClubHandlerTest {
	
	private static String customID = "idModify User240520189229";

	// Uses customID
	private RequestPOJO buildModifyClubRP(String clubId, ClubPOJO club)
	{
		List<Object> l = new ArrayList<Object>();
		l.add(clubId);
		l.add(club);
		
		return new RequestPOJO(customID, new RequestPOJO(RequestType.MODIFY_CLUB, l));
	}
	
	/**
	 * Test to prove that the way to modify users using request works properly.
	 */
	public void modifyUserTest() 
	{
		//We initialize the FrontController
		Initializer.initialize();
		FrontController fc = ProductionConfig.getFrontController();
		
		//The system should have at least one club to modify it, we add the club.
		ClubPOJO club = new ClubPOJO("Id", "Disco1", "Adress street", 10.0F, new HashSet<TagPOJO>());
		
		fc.getProfileManagerSA().addNewClub(club);
		
		//Then we create the RequestPOJO to ask the system to modify the user
		ClubPOJO modifyClub = new ClubPOJO("Id", "titoKimboDisco", null, (Float) null, null);
		
		RequestPOJO rp = buildModifyClubRP(modifyClub.getID(), modifyClub);
		
		ModifyUserHandler handler = new ModifyUserHandler(fc, rp);
		
		//We execute the request
		handler.run();
		
		//We check that the system processed the request and did it successfully.
		assertTrue("Modifying the user failed.", (Boolean) fc.poll(customID).getAnswer().get(0));
		assertEquals("Modifying the user failed.", 
				(String) fc.getProfileManagerSA().getClub("Id").getCommercialName(), "titoKimbo");
	}

}
