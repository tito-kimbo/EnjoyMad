package es.ucm.fdi.business.requesthandling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.UserPOJO;

public class DeleteReviewHandler implements RequestHandler {

	private FrontController fc;
	private RequestPOJO rp;

	public DeleteReviewHandler(FrontController fc){
		this.fc = fc;
	}
	
	public DeleteReviewHandler(FrontController fc, RequestPOJO rp){
		this.fc = fc;
		this.rp = rp;
	}
	
	public void run(){
		//Parse request data
		
		String userID;
		String clubID;
		List<Object> answerData;
		
		userID = (String)rp.getParameters().get(0);
		clubID = (String)rp.getParameters().get(1);
				
		answerData = new ArrayList<Object>();
				
		//	Call relevant ProfileManagerSA methods
		
		try{
			fc.getProfileManagerSA().removeReview(clubID, userID);
			answerData.add(true);
		}catch(NoSuchElementException nsee){
			System.out.println("Invalid user or club  on delete review" + nsee.getMessage());
			answerData.add(false);
		}
		
		finally{
			//	Answer
			fc.addAnswer(rp.getID(), new AnswerPOJO(answerData));
		}
		
	}
	
	public Object clone(RequestPOJO rp){
		return new DeleteReviewHandler(fc, rp);
	}
}
