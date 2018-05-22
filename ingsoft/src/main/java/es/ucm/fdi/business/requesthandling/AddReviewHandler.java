package es.ucm.fdi.business.requesthandling;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.integration.data.ReviewPOJO;

/**
 * Given a RequestPOJO of type AddReview, it tries to add the review to the 
 * current handler.
 */
public class AddReviewHandler implements RequestHandler {

	private FrontController fc;
	private RequestPOJO rp;

	public AddReviewHandler(FrontController fc){
		this.fc = fc;
	}
	
	public AddReviewHandler(FrontController fc, RequestPOJO rp){
		this.fc = fc;
		this.rp = rp;
	}
	
	public void run(){
		//Parse request data
		/*	Data format
		 * 	1. rating (Double)
		 * 	2. opinion (String)
		 * 	3. clubID (String)
		 * 	4. userID (String)
		 */
		double rating;
		ReviewPOJO review;
		List<Object> answerData;
		String opinion, clubID, userID;
		
		
		rating = Double.parseDouble(rp.getParameters().get(0));
		opinion = rp.getParameters().get(1);
		clubID = rp.getParameters().get(2);
		userID = rp.getParameters().get(3);
		
		review = new ReviewPOJO(opinion, rating);
		answerData = new ArrayList<Object>();
		
		//	Call relevant ProfileManager methods
		try{
			fc.getProfileManagerSA().addReview(clubID, review, userID);
			//Indicate operation success
			answerData.add(true);
			
		}catch(DataFormatException dfe){
			System.out.print("Error handling AddReview request " + dfe.getMessage());
			//Indicate operation failure
			answerData.add(false);
		}finally{
			//Answer
			fc.addAnswer(rp.getID(), new AnswerPOJO(answerData));
		}
		
	}
	
	public Object clone(RequestPOJO rp){
		return new AddReviewHandler(fc, rp);
	}
}
