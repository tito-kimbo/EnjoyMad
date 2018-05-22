package es.ucm.fdi.business.requesthandling;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.RequestPOJO;

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
		//Parse request data
		
		//	Call relevant ProfileManagerSA methods
		
		//	Answer
	}
	
	public Object clone(RequestPOJO rp){
		return new ModifyClubHandler(fc, rp);
	}
}
