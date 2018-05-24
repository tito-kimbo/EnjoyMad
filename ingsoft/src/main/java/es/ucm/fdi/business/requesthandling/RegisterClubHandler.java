package es.ucm.fdi.business.requesthandling;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

public class RegisterClubHandler implements RequestHandler {

	private FrontController fc;
	private RequestPOJO rp;
	
	public RegisterClubHandler(FrontController fc){
		this.fc = fc;
	}
	
	public RegisterClubHandler(FrontController fc, RequestPOJO rp){
		this.fc = fc;
		this.rp = rp;
	}
	
	public void run(){
		//Parse rp data
		/*	Data format
		 * 	1. UserPOJO
		 */
		List<Object> answerData = new ArrayList<Object>();
		ClubPOJO club = (ClubPOJO)rp.getParameters().get(0);
		
		//Call methods from pmsa
		try{
			fc.getProfileManagerSA().addNewClub(club);
			answerData.add(true);
		}catch(IllegalArgumentException iae){
			System.out.println("Illegal argument on club register: " + iae.getMessage());
			answerData.add(false);
		}finally{
			//Create answer and add it to fc map
			fc.addAnswer(rp.getID(), new AnswerPOJO(answerData));	
		}
	}
	
	public Object clone(RequestPOJO rp){
		return new RegisterClubHandler(fc, rp);
	}
}
