package es.ucm.fdi.business.requesthandling;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.profilemanagement.ProfileManagerSA;

public class DeleteAccountHandler implements RequestHandler {

	private FrontController fc;
	private RequestPOJO rp;

	public DeleteAccountHandler(FrontController fc) {
		this.fc = fc;
	}

	public DeleteAccountHandler(FrontController fc, RequestPOJO rp) {
		this.fc = fc;
		this.rp = rp;
	}

	public void run() {
		// Parse request data
		String id;
		List<Object> answerData;
		ProfileManagerSA pmsa = fc.getProfileManagerSA();

		id = (String) rp.getParameters().get(0);

		answerData = new ArrayList<Object>();
		// Call relevant ProfileManagerSA methods
		try {
			if (pmsa.hasUser(id)) {

				pmsa.removeUser(id);
				answerData.add(true);
			} else if (pmsa.hasClub(id)) {

				pmsa.removeClub(id);
				answerData.add(true);
			} else {
				throw new NoSuchElementException(
						"There is no account in the system matching id " + id);
			}
		} catch (NoSuchElementException nsee) {
			System.out.println("Invalid user on delete account"
					+ nsee.getMessage());
			answerData.add(false);
		} finally {
			// Answer
			fc.addAnswer(rp.getID(), new AnswerPOJO(answerData));
		}

	}

	public Object clone(RequestPOJO rp) {
		return new DeleteAccountHandler(fc, rp);
	}
}
