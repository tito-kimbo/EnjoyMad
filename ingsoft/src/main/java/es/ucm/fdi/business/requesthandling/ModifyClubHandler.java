package es.ucm.fdi.business.requesthandling;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.Location;

public class ModifyClubHandler implements RequestHandler {

	private FrontController fc;
	private RequestPOJO rp;

	public ModifyClubHandler(FrontController fc){
		this.fc = fc;
	}
	
	public ModifyClubHandler(FrontController fc, RequestPOJO rp){
		this.fc = fc;
		this.rp = rp;
	}
	
	public void run(){
		String clubID;
		List<Object> answerData;
		
		clubID = (String)rp.getParameters().get(0);
		
		answerData = new ArrayList<Object>();
		
		//Waiting for news info on requestPOJO; 
		
		ClubPOJO clubChanges = new ClubPOJO(clubID,
				(String)rp.getParameters().get(1), (String)rp.getParameters().get(2),
				(float)rp.getParameters().get(3), (Location)rp.getParameters().get(4),
				(float)rp.getParameters().get(5)); 
		
		//	Call relevant ProfileManagerSA methods
		
		try{
			fc.getProfileManagerSA().modifyClubData(clubID, clubChanges);
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
		return new ModifyClubHandler(fc, rp);
	}
}
