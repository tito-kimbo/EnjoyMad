package es.ucm.fdi.business.requesthandling;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.RequestPOJO;

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
	
	public void handle(){
		//Parse request data
		
		//	Call relevant ProfileManager methods
		
		//Answer
		
	}
	
	public Object clone(RequestPOJO rp){
		return new AddReviewHandler(fc, rp);
	}
}
