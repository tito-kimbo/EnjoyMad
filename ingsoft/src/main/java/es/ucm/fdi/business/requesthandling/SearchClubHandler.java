package es.ucm.fdi.business.requesthandling;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Given a <code>RequestPOJO</code> of type SearchClub, it executes the search
 * via the <code>SearchEngineSA</code>.
 */
public class SearchClubHandler implements RequestHandler {

	private FrontController fc;
	private RequestPOJO rp;

	public SearchClubHandler(FrontController fc) {
		this.fc = fc;
	}

	public SearchClubHandler(FrontController fc, RequestPOJO rp) {
		this.fc = fc;
		this.rp = rp;
	}

	// This is done due to time constraints on project finalization
	// It is not a good practice and should be avoided if possible
	// It is better to create an interpreter/parser and do proper input
	// management
	@SuppressWarnings("unchecked")
	public void run() {
		// Parse request data
		/*
		 * Data format 
		 * 1. UserID
		 * 2. words (String) 
		 * 3. filters (List<FilterPOJO>) 
		 * (String)
		 */
		UserPOJO user;
		String words, userID;
		List<Object> answerData;
		List<FilterPOJO> filters;

		userID = (String) rp.getParameters().get(0);
		words = (String) rp.getParameters().get(1);
		filters = (List<FilterPOJO>) rp.getParameters().get(2);

		answerData = new ArrayList<Object>();
		//get user from db
		user = fc.getProfileManagerSA().getUser(userID);
		
		if(user == null){
			answerData.add(false);
			
		}else{
			answerData.add(true);
			answerData.add(fc.getSearchEngineSA().search(words, filters, user));
		}
		// Search
		fc.addAnswer(rp.getID(), new AnswerPOJO(answerData));
	}

	public Object clone(RequestPOJO rp) {
		return new SearchClubHandler(fc, rp);
	}
}
