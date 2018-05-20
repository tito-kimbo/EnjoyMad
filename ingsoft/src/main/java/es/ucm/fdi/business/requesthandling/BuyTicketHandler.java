package es.ucm.fdi.business.requesthandling;

import es.ucm.fdi.business.FrontController;
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
	
	public void handle(){
		//Parse request data
		
		//	Call relevant ProfileManagerSA methods
		
		//	Answer
	}
	
	public Object clone(RequestPOJO rp){
		return new BuyTicketHandler(fc, rp);
	}
}
