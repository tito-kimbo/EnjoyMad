package es.ucm.fdi.business.requesthandling;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.RequestPOJO;

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
		
		//	Call relevant ProfileManagerSA methods
		
		//	Answer
	}
	
	public Object clone(RequestPOJO rp){
		return new DeleteReviewHandler(fc, rp);
	}
}
