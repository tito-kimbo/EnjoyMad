package es.ucm.fdi.business.requesthandling;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.profilemanagement.ProfileManagerSA;

public class RegisterHandler implements RequestHandler {

	private FrontController fc;
	private RequestPOJO rp;
	
	public RegisterHandler(FrontController fc){
		this.fc = fc;
	}
	
	public RegisterHandler(FrontController fc, RequestPOJO rp){
		this.fc = fc;
		this.rp = rp;
	}
	
	public void handle(){
		//Parse rp data
		
		//Call methods from pmsa
		
		//Create answer and add it to fc map
	}
	
	public Object clone(RequestPOJO rp){
		return new RegisterHandler(fc, rp);
	}
}
