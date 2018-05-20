package es.ucm.fdi.business.requesthandling;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.RequestPOJO;

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
	
	public void handle(){
		//Parse request data
		
		//	Call relevant ProfileManagerSA methods
		
		//	Answer
	}
	
	public Object clone(RequestPOJO rp){
		return new ModifyUserHandler(fc, rp);
	}
}
