package es.ucm.fdi.business.requesthandling;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;

public class BuyTicketHandler implements RequestHandler {

	private FrontController fc;
	private RequestPOJO rp;

	public BuyTicketHandler(FrontController fc){
		this.fc = fc;
	}
	
	public BuyTicketHandler(FrontController fc, RequestPOJO rp){
		this.fc = fc;
		this.rp = rp;
	}
	
	public void run(){
		//Parse request data
		/*
		 * 	Data format
		 * 	1. userID (String)
		 * 	2. clubID (String)
		 * 
		 */
		String userID, clubID;
		List<Object> answerData;
		
		userID = rp.getParameters().get(0);
		clubID = rp.getParameters().get(1);
		
		answerData = new ArrayList<Object>();
		//	Call relevant TicketManagerSA methods
		try{
			boolean b = fc.getTicketManagerSA().buyTicket(clubID, userID);
			answerData.add(b);
		}catch(NoSuchElementException nsee){
			System.out.println("Invalid user or club ID on ticket purchase, " + nsee.getMessage());
			answerData.add(false);
		}
		finally{
			//	Answer
			fc.addAnswer(rp.getID(), new AnswerPOJO(answerData));
		}
	}
	
	public Object clone(RequestPOJO rp){
		return new BuyTicketHandler(fc, rp);
	}
}
