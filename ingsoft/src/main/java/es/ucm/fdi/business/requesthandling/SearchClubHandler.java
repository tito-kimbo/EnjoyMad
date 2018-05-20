package es.ucm.fdi.business.requesthandling;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.RequestPOJO;

public class SearchClubHandler implements RequestHandler {

	private FrontController fc;
	private RequestPOJO rp;

	public SearchClubHandler(FrontController fc){
		this.fc = fc;
	}
	
	public SearchClubHandler(FrontController fc, RequestPOJO rp){
		this.fc = fc;
		this.rp = rp;
	}
	
	public void handle(){
		//Parse request data
		
		//	Parse words
		
		//	Parse filters
		
		//Search
		
		//
	}
	
	public Object clone(RequestPOJO rp){
		return new SearchClubHandler(fc, rp);
	}
}
