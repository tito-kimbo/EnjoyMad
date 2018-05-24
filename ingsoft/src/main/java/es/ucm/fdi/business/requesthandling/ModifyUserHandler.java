package es.ucm.fdi.business.requesthandling;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.profilemanagement.tools.UserModifierHelper;
import es.ucm.fdi.integration.data.UserPOJO;

public class ModifyUserHandler implements RequestHandler {

	private FrontController fc;
	private RequestPOJO rp;

	public ModifyUserHandler(FrontController fc){
		this.fc = fc;
	}
	
	public ModifyUserHandler(FrontController fc, RequestPOJO rp){
		this.fc = fc;
		this.rp = rp;
	}
	
	public void run(){
		String userID;
		List<Object> answerData;
		
		userID = (String)rp.getParameters().get(0);
		
		answerData = new ArrayList<Object>();
		
		//Waiting for news info on modifyClubData; 
		
		UserPOJO userChanges = new UserPOJO(null, null, null, null, null, null); 

		//	Call relevant ProfileManagerSA methods
		
		try{
			fc.getProfileManagerSA().modifyUserData(userID, userChanges);
			answerData.add(true);
		}catch(NoSuchElementException nsee){
			System.out.println("Invalid club ID or type to be modified" + nsee.getMessage());
			answerData.add(false);
		}
		catch(IllegalArgumentException iae) {
			System.out.println("The new data class does not match that needed for the modification" + iae.getMessage());
			answerData.add(false);
		}
		
		finally{
			//	Answer
			fc.addAnswer(rp.getID(), new AnswerPOJO(answerData));
		}
	}
	
	public Object clone(RequestPOJO rp){
		return new ModifyUserHandler(fc, rp);
	}
}
